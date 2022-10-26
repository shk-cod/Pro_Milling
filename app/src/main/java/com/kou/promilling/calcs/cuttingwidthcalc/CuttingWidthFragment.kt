package com.kou.promilling.calcs.cuttingwidthcalc

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
import com.kou.promilling.databinding.FragmentCuttingWidthBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Fragment of cutting width calculator screen.
 */
@AndroidEntryPoint
@Suppress("Deprecation")
class CuttingWidthFragment : Fragment() {

//    @Inject lateinit var dataSource: MillingDao

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
        val binding = FragmentCuttingWidthBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        val application = requireNotNull(this.activity).application
        val dataSource = getDatabase(application).millingDao
        val item = arguments?.let { CuttingWidthFragmentArgs.fromBundle(it).item }
        val viewModelFactory = CuttingWidthViewModelFactory(dataSource, item, application)
        val viewModel = ViewModelProvider(this, viewModelFactory)[CuttingWidthViewModel::class.java]
        binding.viewModel = viewModel

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.roundingRadiusErrorFlow.collect { error ->
                    Timber.i("flow collected")
                    binding.textInputRoundingRadius.error = error
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

        binding.textInputRoundingRadius.doOnTextChanged { text, _, _, _ ->
            Timber.i("doOnTextChanged")
            if (
                text.isNullOrBlank() ||
                binding.textInputRadius.text.isNullOrBlank()
            ) return@doOnTextChanged

            val roundingRadius = text.toString().toDouble()
            val toolRadius = binding.textInputRadius.text.toString().toDouble()

            Timber.i("$roundingRadius, $toolRadius")
            viewModel.checkRoundingRadius(roundingRadius, toolRadius)
        }

        return binding.root
    }


}