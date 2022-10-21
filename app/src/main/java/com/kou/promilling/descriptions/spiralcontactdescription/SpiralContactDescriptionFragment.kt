package com.kou.promilling.descriptions.spiralcontactdescription

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kou.promilling.R
import com.kou.promilling.databinding.FragmentSpiralContactDescriptionBinding

class SpiralContactDescriptionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSpiralContactDescriptionBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        val viewModel = ViewModelProvider(this)[SpiralContactDescriptionViewModel::class.java]
        binding.viewModel = viewModel


        val app = requireNotNull(activity).application
        binding.schemeDescriptionTextView.text = Html.fromHtml(
            app.getString(R.string.spiral_contact_scheme_html_string),
            Html.FROM_HTML_MODE_LEGACY
        )

        return binding.root
    }





}