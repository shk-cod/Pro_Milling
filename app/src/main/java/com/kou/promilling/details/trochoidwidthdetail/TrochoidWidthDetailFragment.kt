package com.kou.promilling.details.trochoidwidthdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kou.promilling.databinding.TrochoidWidthItemDetailBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment of trochoid width calculation result detail screen.
 */
@AndroidEntryPoint
class TrochoidWidthDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = TrochoidWidthItemDetailBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        val item = TrochoidWidthDetailFragmentArgs.fromBundle(requireArguments()).item
        val viewModel: TrochoidWidthDetailViewModel by viewModels {
            TrochoidWidthDetailViewModelFactory(item)
        }
        binding.viewModel = viewModel

        viewModel.navigateToResults.observe(viewLifecycleOwner) {
            if (it == true) { //navigate back to the calculation results list screen
                this.findNavController().navigate(
                    TrochoidWidthDetailFragmentDirections.actionTrochoidWidthDetailFragmentToResults()
                )
                viewModel.doneNavigatingToResults()
            }
        }

        viewModel.navigateToCalc.observe(viewLifecycleOwner) { trochoidItem ->
            trochoidItem?.let { //navigate to the calculator by reusing calculation parameters
                this.findNavController().navigate(
                    TrochoidWidthDetailFragmentDirections.actionTrochoidWidthDetailFragmentToTrochoidWidth2(
                        it
                    )
                )
                viewModel.doneNavigatingToCalc()
            }
        }

        return binding.root
    }
}