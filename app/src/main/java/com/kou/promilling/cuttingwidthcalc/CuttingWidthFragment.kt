package com.kou.promilling.cuttingwidthcalc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.kou.promilling.database.getDatabase
import com.kou.promilling.databinding.FragmentCuttingWidthBinding

class CuttingWidthFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCuttingWidthBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val dataSource = getDatabase(application).millingDao
        val item = arguments?.let { CuttingWidthFragmentArgs.fromBundle(it).item }
        val viewModelFactory = CuttingWidthViewModelFactory(dataSource, item)
        val viewModel = ViewModelProvider(this, viewModelFactory)[CuttingWidthViewModel::class.java]
        binding.viewModel = viewModel

        return binding.root
    }


}