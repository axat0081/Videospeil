package com.example.videospeil.authentication

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.videospeil.R
import com.example.videospeil.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment(R.layout.fragment_registration) {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegistrationBinding.bind(view)
        binding.apply {

        }
    }
    fun sendUserToHome(){
        val action = RegistrationFragmentDirections.actionRegistrationFragmentToHomeFragment()
        findNavController().navigate(action)
    }
    fun sendUserToLogin(){
        val action = RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment()
        findNavController().navigate(action)
    }
}