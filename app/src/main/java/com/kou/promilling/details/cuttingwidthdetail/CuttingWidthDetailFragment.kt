package com.kou.promilling.details.cuttingwidthdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kou.promilling.database.DatabaseCuttingWidth
import com.kou.promilling.databinding.CuttingWidthItemDetailBinding

class CuttingWidthDetailFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = CuttingWidthItemDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        @Suppress("unresolved_reference")
        //TODO: add item from navigation arguments!!!
        val viewModelFactory = CuttingWidthDetailViewModelFactory(item)
        binding.viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CuttingWidthDetailViewModel::class.java)

        return binding.root
    }
}