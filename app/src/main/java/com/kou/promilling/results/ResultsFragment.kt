package com.kou.promilling.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kou.promilling.database.*
import com.kou.promilling.databinding.FragmentResultsBinding

class ResultsFragment : Fragment() {

    private lateinit var viewModel: ResultsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentResultsBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        val application = requireNotNull(this.activity).application
        val dataSource = getDatabase(application).millingDao
        val viewModelFactory = ResultsViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory)[ResultsViewModel::class.java]
        binding.viewModel = viewModel

        viewModel.navigateToItemDetail.observe(viewLifecycleOwner) { item ->
            item?.let {
                val navController = this.findNavController()
                when (it.type) {
                    EntityType.TYPE_SPIRAL_CONTACT -> {
                        navController.navigate(ResultsFragmentDirections.actionResultsToSpiralContactDetailFragment(it))
                    }
                    EntityType.TYPE_CUTTING_WIDTH -> {
                        navController.navigate(ResultsFragmentDirections.actionResultsToCuttingWidthDetailFragment(it))
                    }
                    EntityType.TYPE_TROCHOID_WIDTH -> {
                        navController.navigate(ResultsFragmentDirections.actionResultsToTrochoidWidthDetailFragment(it))
                    }
                    else -> throw Exception("Wrong type")
                }
                viewModel.displayItemDetailComplete()
            }
        }


        val adapter = ResultsAdapter(ResultsAdapter.OnClickListener {
            viewModel.displayItemDetail(it)
        }, application)
        binding.resultsList.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner) {
            adapter.submitData(it)
        }


        return binding.root
    }
}