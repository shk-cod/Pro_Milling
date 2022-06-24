package com.kou.promilling.results

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kou.promilling.database.ResultItem
import com.kou.promilling.databinding.ResultItemViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResultsAdapter(private val onClickListener: OnClickListener): ListAdapter<ResultItem, ViewHolder>(ResultItemDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val resultItem = getItem(position) as ResultItem
        holder.itemView.setOnClickListener {
            onClickListener.onClick(resultItem)
        }
        holder.bind(resultItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    fun submitData(list: List<ResultItem>?) {
        adapterScope.launch {
            withContext(Dispatchers.Main) {
                submitList(list)
            }
        }
    }

    class OnClickListener(val clickListener: (item: ResultItem) -> Unit) {
        fun onClick(item: ResultItem) = clickListener(item)
    }
}

class ViewHolder private constructor(val binding: ResultItemViewBinding): RecyclerView.ViewHolder(binding.root){
    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ResultItemViewBinding.inflate(
                layoutInflater,
                parent,
                false
            )

            return ViewHolder(binding)
        }
    }

    fun bind(item: ResultItem) {
        binding.resultItem = item
        binding.executePendingBindings()
    }
}

class ResultItemDiffCallback: DiffUtil.ItemCallback<ResultItem>() {
    override fun areItemsTheSame(oldItem: ResultItem, newItem: ResultItem): Boolean {
        return oldItem.id == newItem.id && oldItem.type == newItem.type
    }

    override fun areContentsTheSame(oldItem: ResultItem, newItem: ResultItem): Boolean {
        return oldItem.type == newItem.type &&
                oldItem.result == newItem.result &&
                oldItem.date == newItem.date
    }
}
