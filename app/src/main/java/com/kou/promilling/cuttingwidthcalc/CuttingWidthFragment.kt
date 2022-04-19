package com.kou.promilling.cuttingwidthcalc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.kou.promilling.R
import com.kou.promilling.databinding.FragmentCuttingWidthBinding


class CuttingWidthFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCuttingWidthBinding.inflate(inflater)
        val viewModel = ViewModelProvider(this).get(CuttingWidthViewModel::class.java)
        binding.viewModel = viewModel

        return binding.root
    }


}