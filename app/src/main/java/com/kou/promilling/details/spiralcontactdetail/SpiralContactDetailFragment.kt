package com.kou.promilling.details.spiralcontactdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kou.promilling.databinding.SpiralContactItemDetailBinding

/**
 * Fragment of spiral contact length calculation result detail screen.
 */
class SpiralContactDetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val app = requireNotNull(activity).application
        val binding = SpiralContactItemDetailBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        val item = SpiralContactDetailFragmentArgs.fromBundle(requireArguments()).item
        val viewModelFactory = SpiralContactDetailViewModelFactory(item, app)
        val viewModel = ViewModelProvider(this, viewModelFactory)[SpiralContactDetailViewModel::class.java]
        binding.viewModel = viewModel

        viewModel.navigateToResults.observe(viewLifecycleOwner) {
            if (it == true) { //navigate back to the calculation results list screen
                this.findNavController().navigate(
                    SpiralContactDetailFragmentDirections.actionSpiralContactDetailFragmentToResults()
                )
                viewModel.doneNavigatingToResults()
            }
        }

        viewModel.navigateToCalc.observe(viewLifecycleOwner) { spiralItem ->
            spiralItem?.let { //navigate to the calculator by reusing calculation parameters
                this.findNavController().navigate(
                    SpiralContactDetailFragmentDirections.actionSpiralContactDetailFragmentToSpiralContact2(
                        it
                    )
                )
                viewModel.doneNavigatingToCalc()
            }

        }

        return binding.root
    }
}