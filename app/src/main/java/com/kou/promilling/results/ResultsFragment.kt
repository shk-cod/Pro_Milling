package com.kou.promilling.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.kou.promilling.database.*
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

        viewModel.navigateToItemDetail.observe(viewLifecycleOwner) { item ->
            item?.let {
                val navController = this.findNavController()
                when (it) {
                    is DatabaseSpiralContactLength -> {
                        navController.navigate(ResultsFragmentDirections.actionResultsToSpiralContactDetailFragment(it))
                    }
                    is DatabaseCuttingWidth -> {
                        navController.navigate(ResultsFragmentDirections.actionResultsToCuttingWidthDetailFragment(it))
                    }
                    is DatabaseTrochoidWidth -> {
                        navController.navigate(ResultsFragmentDirections.actionResultsToTrochoidWidthDetailFragment(it))
                    }
                }
                viewModel.displayItemDetailComplete()
            }
        }


        val adapter = ResultsAdapter(ResultsAdapter.OnClickListener {
            viewModel.displayItemDetail(it)
        }, application)
        binding.resultsList.adapter = adapter

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