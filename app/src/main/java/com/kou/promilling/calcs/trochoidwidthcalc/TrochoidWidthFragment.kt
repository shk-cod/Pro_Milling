package com.kou.promilling.calcs.trochoidwidthcalc

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
import com.kou.promilling.database.getDatabase
import com.kou.promilling.databinding.FragmentTrochoidWidthBinding
import kotlinx.coroutines.launch

/**
 * Fragment of trochoid width calculator screen.
 */
@Suppress("Deprecation")
class TrochoidWidthFragment : Fragment() {

    private lateinit var binding: FragmentTrochoidWidthBinding
    private lateinit var viewModel: TrochoidWidthViewModel

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
                    TrochoidWidthFragmentDirections.actionTrochoidWidthToTrochoidWidthDescriptionFragment()
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
        binding = FragmentTrochoidWidthBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        val application = requireNotNull(this.activity).application
        val dataSource = getDatabase(application).millingDao
        val item = arguments?.let { TrochoidWidthFragmentArgs.fromBundle(it).item }
        val viewModelFactory = TrochoidWidthViewModelFactory(dataSource, item, application)
        viewModel = ViewModelProvider(this, viewModelFactory)[TrochoidWidthViewModel::class.java]
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
                viewModel.trochoidStepErrorFlow.collect { error ->
                    binding.textInputTrochoidStep.error = error
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

        binding.textInputTrochoidStep.doOnTextChanged { _, _, _, _ ->
            checkTextFields()
        }

        return binding.root
    }

    private fun checkTextFields() {
        val toolRadiusText = binding.textInputRadius.text
        val roundingRadiusText = binding.textInputRoundingRadius.text
        val trochoidStepText = binding.textInputTrochoidStep.text

        if (
            toolRadiusText.isNullOrBlank() ||
            roundingRadiusText.isNullOrBlank() ||
            trochoidStepText.isNullOrBlank()
        ) return

        val toolRadius = toolRadiusText.toString().toDouble()
        val roundingRadius = roundingRadiusText.toString().toDouble()
        val trochoidStep = trochoidStepText.toString().toDouble()

        viewModel.checkToolRadius(toolRadius, trochoidStep)
        viewModel.checkRoundingRadius(roundingRadius)
        viewModel.checkTrochoidStep(trochoidStep, toolRadius)
    }


}