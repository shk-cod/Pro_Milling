package com.kou.promilling.trochoidwidthcalc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.kou.promilling.R
import com.kou.promilling.databinding.FragmentTrochoidWidthBinding

class TrochoidWidthFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTrochoidWidthBinding.inflate(inflater)
        val viewModel = ViewModelProvider(this).get(TrochoidWidthViewModel::class.java)
        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        return binding.root
    }


}