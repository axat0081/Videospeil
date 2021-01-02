package com.example.videospeil.authentication

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.videospeil.R
import com.example.videospeil.databinding.FragmentLoginBinding

class LoginFragment:Fragment(R.layout.fragment_login) {
    private var _binding:FragmentLoginBinding?=null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)
        binding.apply {

        }
    }
    fun sendUserToRegistration(){
        val action = LoginFragmentDirections.actionLoginFragmentToRegistrationFragment()
        findNavController().navigate(action)
    }
    fun sendUserToHome(){
        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        findNavController().navigate(action)
    }
}
