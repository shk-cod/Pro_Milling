package com.kou.promilling.descriptions.cuttingwidthdescription

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kou.promilling.databinding.FragmentCuttingWidthDescriptionBinding

/**
 * Fragment of cutting width description screen.
 */
class CuttingWidthDescriptionFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCuttingWidthDescriptionBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
}