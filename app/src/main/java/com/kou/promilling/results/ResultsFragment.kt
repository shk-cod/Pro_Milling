package com.kou.promilling.results

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kou.promilling.R
import com.kou.promilling.databinding.FragmentResultsBinding

class ResultsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentResultsBinding.inflate(inflater)
        val viewModel = ViewModelProvider(this).get(ResultsViewModel::class.java)
        binding.viewModel = viewModel

        return binding.root
    }


}