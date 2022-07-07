package com.kou.promilling.calcs.trochoidwidthcalc

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
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
        val item = arguments?.let { TrochoidWidthFragmentArgs.fromBundle(it).item }
        val viewModelFactory = TrochoidWidthViewModelFactory(dataSource, item)
        val viewModel = ViewModelProvider(this, viewModelFactory)[TrochoidWidthViewModel::class.java]
        binding.viewModel = viewModel

        binding.textViewResult.doOnTextChanged { _, _, _, _ ->
            binding.scrollView.scrollTo(0, binding.textViewResult.scrollY)
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
        }

        return binding.root
    }


}