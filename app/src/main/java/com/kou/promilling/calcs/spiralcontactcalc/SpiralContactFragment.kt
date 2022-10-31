package com.kou.promilling.calcs.spiralcontactcalc

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
import com.kou.promilling.databinding.FragmentSpiralContactBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Fragment of spiral contact length calculator screen.
 */
@Suppress("Deprecation")
@AndroidEntryPoint
class SpiralContactFragment : Fragment() {

    @Inject
    lateinit var dataSource: MillingDao
    private lateinit var binding: FragmentSpiralContactBinding
    private lateinit var viewModel: SpiralContactViewModel

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
                    SpiralContactFragmentDirections.actionSpiralContactToSpiralContactDescriptionFragment()
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
        binding = FragmentSpiralContactBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        val application = requireNotNull(this.activity).application
        val item = arguments?.let { SpiralContactFragmentArgs.fromBundle(it).item }
        val viewModelFactory = SpiralContactViewModelFactory(dataSource, item, application)
        viewModel = ViewModelProvider(this, viewModelFactory)[SpiralContactViewModel::class.java]
        binding.viewModel = viewModel


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.toolDiameterErrorFlow.collect { error ->
                    binding.textInputDiameter.error = error
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.spiralAngleErrorFlow.collect { error ->
                    binding.textInputSpiralAngle.error = error
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cuttingHeightErrorFlow.collect { error ->
                    binding.textInputCuttingHeight.error = error
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

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.fluteCountErrorFlow.collect { error ->
                    binding.textInputFluteCount.error = error
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.flutePositionErrorFlow.collect { error ->
                    binding.textInputFlutePosition.error = error
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

        binding.textInputDiameter.doOnTextChanged { _, _, _, _ ->
            checkTextFields()
        }

        binding.textInputSpiralAngle.doOnTextChanged { _, _, _, _ ->
            checkTextFields()
        }

        binding.textInputCuttingHeight.doOnTextChanged { _, _, _, _ ->
            checkTextFields()
        }

        binding.textInputCuttingWidth.doOnTextChanged { _, _, _, _ ->
            checkTextFields()
        }

        binding.textInputFluteCount.doOnTextChanged { _, _, _, _ ->
            checkTextFields()
        }

        binding.textInputFlutePosition.doOnTextChanged { _, _, _, _ ->
            checkTextFields()
        }

        return binding.root
    }

    private fun checkTextFields() {
        val toolDiameterText = binding.textInputDiameter.text
        val spiralAngleText = binding.textInputSpiralAngle.text
        val cuttingHeightText = binding.textInputCuttingHeight.text
        val cuttingWidthText = binding.textInputCuttingWidth.text
        val fluteCountText = binding.textInputFluteCount.text
        val flutePositionText = binding.textInputFlutePosition.text

        if (
            toolDiameterText.isNullOrBlank() ||
            spiralAngleText.isNullOrBlank() ||
            cuttingHeightText.isNullOrBlank() ||
            cuttingWidthText.isNullOrBlank() ||
            fluteCountText.isNullOrBlank() ||
            flutePositionText.isNullOrBlank()
        ) return

        val toolDiameter = toolDiameterText.toString().toDouble()
        val spiralAngle = spiralAngleText.toString().toDouble()
        val cuttingHeight = cuttingHeightText.toString().toDouble()
        val cuttingWidth = cuttingWidthText.toString().toDouble()
        val fluteCount = fluteCountText.toString().toInt()
        val flutePosition = flutePositionText.toString().toDouble()

        viewModel.checkToolDiameter(toolDiameter, cuttingWidth)
        viewModel.checkSpiralAngle(spiralAngle)
        viewModel.checkCuttingHeight(cuttingHeight)
        viewModel.checkCuttingWidth(cuttingWidth, toolDiameter)
        viewModel.checkFluteCount(fluteCount)
        viewModel.checkFlutePosition(flutePosition)
    }
}