package com.kou.promilling.details.trochoidwidthdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kou.promilling.databinding.TrochoidWidthItemDetailBinding

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
        binding.viewModel = ViewModelProvider(this, viewModelFactory)
            .get(TrochoidWidthDetailViewModel::class.java)

        return binding.root
    }
}