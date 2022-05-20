package com.kou.promilling.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.kou.promilling.database.ResultItem
import com.kou.promilling.database.getDatabase
import com.kou.promilling.databinding.FragmentResultsBinding

class ResultsFragment : Fragment() {

    private lateinit var viewModel: ResultsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentResultsBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val dataSource = getDatabase(application).millingDao
        val viewModelFactory = ResultsViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory)[ResultsViewModel::class.java]
        binding.viewModel = viewModel


        val adapter = ResultsAdapter()
        binding.resultsList.adapter = adapter
        //TODO: deal with item decoration
        binding.resultsList.addItemDecoration(DividerItemDecoration(
            binding.resultsList.context,
            DividerItemDecoration.VERTICAL
        ))

        viewModel.spiral.observe(viewLifecycleOwner) {
            adapter.submitData(buildList())
        }

        viewModel.cutting.observe(viewLifecycleOwner) {
            adapter.submitData(buildList())
        }

        viewModel.trochoid.observe(viewLifecycleOwner) {
            adapter.submitData(buildList())
        }



        return binding.root
    }

    private fun buildList(): List<ResultItem> {
        val list = mutableListOf<ResultItem>()
        viewModel.spiral.value?.let { list.addAll(it) }
        viewModel.cutting.value?.let { list.addAll(it) }
        viewModel.trochoid.value?.let { list.addAll(it) }

        list.sortByDescending { it.date }


        return list
    }


}