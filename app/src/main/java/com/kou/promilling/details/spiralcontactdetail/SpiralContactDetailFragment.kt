package com.kou.promilling.details.spiralcontactdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kou.promilling.databinding.SpiralContactItemDetailBinding

class SpiralContactDetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val app = requireNotNull(activity).application
        val binding = SpiralContactItemDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val item = SpiralContactDetailFragmentArgs.fromBundle(arguments!!).item
        val viewModelFactory = SpiralContactDetailViewModelFactory(item, app)
        binding.viewModel = ViewModelProvider(this, viewModelFactory)
            .get(SpiralContactDetailViewModel::class.java)

        return binding.root
    }
}