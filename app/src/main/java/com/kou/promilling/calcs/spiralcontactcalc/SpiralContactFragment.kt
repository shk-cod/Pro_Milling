package com.kou.promilling.calcs.spiralcontactcalc

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
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
        binding.lifecycleOwner = viewLifecycleOwner

        val application = requireNotNull(this.activity).application
        val dataSource = getDatabase(application).millingDao
        val item = arguments?.let { SpiralContactFragmentArgs.fromBundle(it).item }
        val viewModelFactory = SpiralContactViewModelFactory(dataSource, item, application)
        val viewModel = ViewModelProvider(this, viewModelFactory)[SpiralContactViewModel::class.java]
        binding.viewModel = viewModel


        binding.buttonResult.setOnClickListener {
            if (!viewModel.result()) return@setOnClickListener
            binding.scrollView.scrollTo(0, binding.textViewResult.scrollY)
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
            activity?.currentFocus?.clearFocus()
        }

        return binding.root
    }


}