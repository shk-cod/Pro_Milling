package com.kou.promilling.spiralcontactcalc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kou.promilling.database.getDatabase
import com.kou.promilling.databinding.FragmentSpiralContactBinding

class SpiralContactFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSpiralContactBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val dataSource = getDatabase(application).millingDao
        val viewModelFactory = SpiralContactViewModelFactory(dataSource)
        val viewModel = ViewModelProvider(this, viewModelFactory)[SpiralContactViewModel::class.java]
        binding.viewModel = viewModel

        return binding.root
    }


}