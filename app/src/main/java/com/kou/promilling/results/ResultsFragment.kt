package com.kou.promilling.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

        viewModel.results.observe(viewLifecycleOwner) {
            it?.let {
                adapter.data = it
            }
        }



        return binding.root
    }


}