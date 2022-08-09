package com.kou.promilling.details.spiralcontactdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kou.promilling.databinding.SpiralContactItemDetailBinding

class SpiralContactDetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val app = requireNotNull(activity).application
        val binding = SpiralContactItemDetailBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        val item = SpiralContactDetailFragmentArgs.fromBundle(arguments!!).item
        val viewModelFactory = SpiralContactDetailViewModelFactory(item, app)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(SpiralContactDetailViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.navigateToResults.observe(viewLifecycleOwner) {
            if (it == true) {
                this.findNavController().navigate(
                    SpiralContactDetailFragmentDirections.actionSpiralContactDetailFragmentToResults()
                )
                viewModel.doneNavigatingToResults()
            }
        }

        viewModel.navigateToCalc.observe(viewLifecycleOwner) { spiralItem ->
            spiralItem?.let {
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