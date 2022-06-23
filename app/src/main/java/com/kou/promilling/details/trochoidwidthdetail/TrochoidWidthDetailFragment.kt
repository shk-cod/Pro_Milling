package com.kou.promilling.details.trochoidwidthdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kou.promilling.databinding.TrochoidWidthItemDetailBinding
import com.kou.promilling.trochoidwidthcalc.TrochoidWidthViewModelFactory

class TrochoidWidthDetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = TrochoidWidthItemDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        @Suppress("unresolved_reference")
        val viewModelFactory = TrochoidWidthViewModelFactory(item)
        binding.viewModel = ViewModelProvider(this, viewModelFactory)
            .get(TrochoidWidthDetailViewModel::class.java)

        return binding.root
    }
}