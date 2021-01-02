package com.example.videospeil.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.videospeil.R
import com.example.videospeil.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailsBinding.bind(view)
        binding.apply {

        }
    }

}