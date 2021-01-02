package com.example.videospeil.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.videospeil.R
import com.example.videospeil.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment:Fragment(R.layout.fragment_home) {
    private var _binding:FragmentHomeBinding?=null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        binding.apply {

        }
    }
    fun sendUserToLogin(){
        val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
        findNavController().navigate(action)
    }
    fun sendUserToGameLists(){
        val action = HomeFragmentDirections.actionHomeFragmentToGamesListFragment()
        findNavController().navigate(action)
    }
}