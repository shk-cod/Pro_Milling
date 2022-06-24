package com.kou.promilling.details.cuttingwidthdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kou.promilling.databinding.CuttingWidthItemDetailBinding

class CuttingWidthDetailFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val app = requireNotNull(activity).application
        val binding = CuttingWidthItemDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val item = CuttingWidthDetailFragmentArgs.fromBundle(arguments!!).item
        val viewModelFactory = CuttingWidthDetailViewModelFactory(item, app)
        binding.viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CuttingWidthDetailViewModel::class.java)

        return binding.root
    }
}