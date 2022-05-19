package com.kou.promilling.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.kou.promilling.database.getDatabase
import com.kou.promilling.databinding.FragmentResultsBinding

class ResultsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentResultsBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val dataSource = getDatabase(application).millingDao
        val viewModelFactory = ResultsViewModelFactory(dataSource)
        val viewModel = ViewModelProvider(this, viewModelFactory)[ResultsViewModel::class.java]
        binding.viewModel = viewModel


        val adapter = ResultsAdapter()
        binding.resultsList.adapter = adapter
        //TODO: deal with item decoration
        binding.resultsList.addItemDecoration(DividerItemDecoration(
            binding.resultsList.context,
            DividerItemDecoration.VERTICAL
        ))

        viewModel.results.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitData(it)
            }
        }



        return binding.root
    }


}