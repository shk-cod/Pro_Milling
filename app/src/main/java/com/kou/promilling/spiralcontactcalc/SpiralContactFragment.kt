package com.kou.promilling.spiralcontactcalc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kou.promilling.R
import com.kou.promilling.databinding.FragmentSpiralContactBinding

class SpiralContactFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSpiralContactBinding.inflate(inflater)
        val viewModel = ViewModelProvider(this).get(SpiralContactViewModel::class.java)
        binding.viewModel = viewModel

        binding.lifecycleOwner = this


        return binding.root
    }


}