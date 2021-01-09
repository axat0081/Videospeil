package com.example.videospeil.authentication

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.videospeil.R
import com.example.videospeil.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val mAuth = FirebaseAuth.getInstance()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)
        if(mAuth.currentUser!=null){
            sendUserToHome()
        }
        binding.apply {
            needNewAccountTextView.setOnClickListener {
                sendUserToRegistration()
            }
            loginButton.setOnClickListener {
                val email = emailTextView.text.toString().trim()
                val password = passwordTextView.text.toString().trim()
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(context, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show()
                } else {
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task->
                        if(task.isSuccessful){
                            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                            sendUserToHome()
                        }else{
                            Toast.makeText(context, "Error: "+task.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun sendUserToRegistration() {
        val action = LoginFragmentDirections.actionLoginFragmentToRegistrationFragment()
        findNavController().navigate(action)
    }

    private fun sendUserToHome() {
        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        findNavController().navigate(action)
    }
}
