package com.kou.promilling.details.trochoidwidthdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kou.promilling.databinding.TrochoidWidthItemDetailBinding
import com.kou.promilling.details.cuttingwidthdetail.CuttingWidthDetailFragmentDirections

class TrochoidWidthDetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val app = requireNotNull(activity).application
        val binding = TrochoidWidthItemDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val item = TrochoidWidthDetailFragmentArgs.fromBundle(arguments!!).item
        val viewModelFactory = TrochoidWidthDetailViewModelFactory(item, app)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(TrochoidWidthDetailViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.navigateToResults.observe(viewLifecycleOwner) {
            if (it == true) {
                this.findNavController().navigate(
                    TrochoidWidthDetailFragmentDirections.actionTrochoidWidthDetailFragmentToResults()
                )
                viewModel.doneNavigatingToResults()
            }
        }

        return binding.root
    }
}