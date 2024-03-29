package com.kou.promilling.descriptions.trochoidwidthdescription

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kou.promilling.databinding.FragmentTrochoidWidthDescriptionBinding

/**
 * Fragment of trochoid width description screen.
 */
class TrochoidWidthDescriptionFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTrochoidWidthDescriptionBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
}