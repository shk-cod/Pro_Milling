package com.kou.promilling.results

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kou.promilling.database.EntityType
import com.kou.promilling.database.MillingDao
import com.kou.promilling.databinding.FragmentResultsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Fragment of calculation results list screen.
 */
@AndroidEntryPoint
class ResultsFragment : Fragment() {


    @Inject lateinit var dataSource: MillingDao
    @Inject lateinit var application: Application

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentResultsBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        val viewModel: ResultsViewModel by viewModels {
            ResultsViewModelFactory(dataSource)
        }
        binding.viewModel = viewModel

        viewModel.navigateToItemDetail.observe(viewLifecycleOwner) { item ->
            item?.let {
                val navController = this.findNavController()
                when (it.type) { //navigate to the details screen depending on calculation type
                    EntityType.TYPE_SPIRAL_CONTACT -> {
                        navController.navigate(
                            ResultsFragmentDirections.actionResultsToSpiralContactDetailFragment(
                                it
                            )
                        )
                    }
                    EntityType.TYPE_CUTTING_WIDTH -> {
                        navController.navigate(
                            ResultsFragmentDirections.actionResultsToCuttingWidthDetailFragment(
                                it
                            )
                        )
                    }
                    EntityType.TYPE_TROCHOID_WIDTH -> {
                        navController.navigate(
                            ResultsFragmentDirections.actionResultsToTrochoidWidthDetailFragment(
                                it
                            )
                        )
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