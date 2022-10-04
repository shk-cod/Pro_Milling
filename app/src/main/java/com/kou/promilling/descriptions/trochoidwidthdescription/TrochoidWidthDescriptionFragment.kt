package com.kou.promilling.descriptions.trochoidwidthdescription

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kou.promilling.R
import com.kou.promilling.databinding.FragmentTrochoidWidthDescriptionBinding

class TrochoidWidthDescriptionFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTrochoidWidthDescriptionBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        val viewModel = ViewModelProvider(this)[TrochoidWidthDescriptionViewModel::class.java]
        binding.viewModel = viewModel

        val app = requireNotNull(activity).application
        binding.schemeDescriptionTextView.text = Html.fromHtml(
            app.getString(R.string.trochoid_width_scheme_html_string),
            Html.FROM_HTML_MODE_LEGACY
        )

        return binding.root
    }
}