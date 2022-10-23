package com.kou.promilling.descriptions.spiralcontactdescription

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kou.promilling.databinding.FragmentSpiralContactDescriptionBinding

/**
 * Fragment of spiral contact length description screen.
 */
class SpiralContactDescriptionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSpiralContactDescriptionBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }


}