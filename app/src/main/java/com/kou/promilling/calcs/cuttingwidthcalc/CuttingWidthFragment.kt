package com.kou.promilling.calcs.cuttingwidthcalc

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.kou.promilling.R
import com.kou.promilling.database.MillingDao
import com.kou.promilling.databinding.FragmentCuttingWidthBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Fragment of cutting width calculator screen.
 */
@AndroidEntryPoint
@Suppress("Deprecation")
class CuttingWidthFragment : Fragment() {

    @Inject lateinit var dataSource: MillingDao
    @Inject lateinit var application: Application

    private lateinit var binding: FragmentCuttingWidthBinding
    private lateinit var viewModel: CuttingWidthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    //TODO: deal with deprecated options menu
    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("inflater.inflate(R.menu.top_app_bar, menu)", "com.kou.promilling.R")
    )
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_app_bar, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            //Navigates to the description screen
            R.id.description -> {
                this.findNavController().navigate(
                    CuttingWidthFragmentDirections.actionCuttingWidthToCuttingWidthDescriptionFragment()
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCuttingWidthBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        val item = arguments?.let { CuttingWidthFragmentArgs.fromBundle(it).item }
        val viewModelFactory = CuttingWidthViewModelFactory(dataSource, item, application)
        viewModel = ViewModelProvider(this, viewModelFactory)[CuttingWidthViewModel::class.java]
        binding.viewModel = viewModel


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.toolRadiusErrorFlow.collect { error ->
                    binding.textInputRadius.error = error
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.roundingRadiusErrorFlow.collect { error ->
                    binding.textInputRoundingRadius.error = error
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cuttingWidthErrorFlow.collect { error ->
                    binding.textInputCuttingWidth.error = error
                }
            }
        }

        /*
        When the "calculate" button is pressed (only if the input checking was successful),
        scrolls to the top of the screen, hides the keyboard and clears the focus.
         */
        binding.buttonResult.setOnClickListener {
            if (!viewModel.result()) return@setOnClickListener
            binding.scrollView.scrollTo(0, binding.textViewResult.scrollY)
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
            activity?.currentFocus?.clearFocus()
        }

        binding.textInputRadius.doOnTextChanged { _, _, _, _ ->
            checkTextFields()
        }

        binding.textInputRoundingRadius.doOnTextChanged { _, _, _, _ ->
            checkTextFields()
        }

        binding.textInputCuttingWidth.doOnTextChanged { _, _, _, _ ->
            checkTextFields()
        }

        return binding.root
    }

    private fun checkTextFields() {
        val toolRadiusText = binding.textInputRadius.text
        val roundingRadiusText = binding.textInputRoundingRadius.text
        val cuttingWidthText = binding.textInputCuttingWidth.text

        if (
            toolRadiusText.isNullOrBlank() ||
            roundingRadiusText.isNullOrBlank() ||
            cuttingWidthText.isNullOrBlank()
        ) return

        val toolRadius = toolRadiusText.toString().toDouble()
        val roundingRadius = roundingRadiusText.toString().toDouble()
        val cuttingWidth = cuttingWidthText.toString().toDouble()

        viewModel.checkToolRadius(toolRadius, roundingRadius, cuttingWidth)
        viewModel.checkRoundingRadius(roundingRadius, toolRadius)
        viewModel.checkCuttingWidth(cuttingWidth, toolRadius)
    }
}