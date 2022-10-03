package com.kou.promilling.calcs.trochoidwidthcalc

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kou.promilling.R
import com.kou.promilling.database.getDatabase
import com.kou.promilling.databinding.FragmentTrochoidWidthBinding

@Suppress("Deprecation")
class TrochoidWidthFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    @Deprecated("Deprecated in Java",
        ReplaceWith("inflater.inflate(R.menu.top_app_bar, menu)", "com.kou.promilling.R")
    )
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_app_bar, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.description -> {
                //navigate to description screen
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTrochoidWidthBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        val application = requireNotNull(this.activity).application
        val dataSource = getDatabase(application).millingDao
        val item = arguments?.let { TrochoidWidthFragmentArgs.fromBundle(it).item }
        val viewModelFactory = TrochoidWidthViewModelFactory(dataSource, item, application)
        val viewModel = ViewModelProvider(this, viewModelFactory)[TrochoidWidthViewModel::class.java]
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