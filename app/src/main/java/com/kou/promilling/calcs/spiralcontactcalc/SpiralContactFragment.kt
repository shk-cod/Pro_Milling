package com.kou.promilling.calcs.spiralcontactcalc

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kou.promilling.R
import com.kou.promilling.database.getDatabase
import com.kou.promilling.databinding.FragmentSpiralContactBinding

/**
 * Fragment of spiral contact length calculator screen.
 */
@Suppress("Deprecation")
class SpiralContactFragment : Fragment() {

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
        val binding = FragmentSpiralContactBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        val application = requireNotNull(this.activity).application
        val dataSource = getDatabase(application).millingDao
        val item = arguments?.let { SpiralContactFragmentArgs.fromBundle(it).item }
        val viewModelFactory = SpiralContactViewModelFactory(dataSource, item, application)
        val viewModel =
            ViewModelProvider(this, viewModelFactory)[SpiralContactViewModel::class.java]
        binding.viewModel = viewModel


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

        return binding.root
    }


}