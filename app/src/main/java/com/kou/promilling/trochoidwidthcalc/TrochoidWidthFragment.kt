package com.kou.promilling.trochoidwidthcalc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.kou.promilling.database.getDatabase
import com.kou.promilling.databinding.FragmentTrochoidWidthBinding

class TrochoidWidthFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTrochoidWidthBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val dataSource = getDatabase(application).millingDao
        val viewModelFactory = TrochoidWidthViewModelFactory(dataSource)
        val viewModel = ViewModelProvider(this, viewModelFactory)[TrochoidWidthViewModel::class.java]
        binding.viewModel = viewModel

        return binding.root
    }


}