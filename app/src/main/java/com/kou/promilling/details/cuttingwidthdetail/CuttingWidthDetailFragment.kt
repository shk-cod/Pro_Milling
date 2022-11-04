@file:Suppress("ReplaceGetOrSet")

package com.kou.promilling.details.cuttingwidthdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kou.promilling.databinding.CuttingWidthItemDetailBinding

/**
 * Fragment of cutting width calculation result detail screen.
 */
class CuttingWidthDetailFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = CuttingWidthItemDetailBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        val item = CuttingWidthDetailFragmentArgs.fromBundle(requireArguments()).item
        val viewModel: CuttingWidthDetailViewModel by viewModels {
            CuttingWidthDetailViewModelFactory(item)
        }
        binding.viewModel = viewModel

        viewModel.navigateToResults.observe(viewLifecycleOwner) {
            if (it == true) { //navigate back to the calculation results list screen
                this.findNavController().navigate(
                    CuttingWidthDetailFragmentDirections.actionCuttingWidthDetailFragmentToResults()
                )
                viewModel.doneNavigatingToResults()
            }
        }

        viewModel.navigateToCalc.observe(viewLifecycleOwner) { cuttingItem ->
            cuttingItem?.let { //navigate to the calculator by reusing calculation parameters
                this.findNavController().navigate(
                    CuttingWidthDetailFragmentDirections.actionCuttingWidthDetailFragmentToCuttingWidth2(
                        it
                    )
                )
                viewModel.doneNavigatingToCalc()
            }
        }

        return binding.root
    }
}