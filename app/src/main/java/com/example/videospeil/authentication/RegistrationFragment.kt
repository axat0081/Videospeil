package com.example.videospeil.authentication

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.videospeil.R
import com.example.videospeil.databinding.FragmentRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistrationFragment : Fragment(R.layout.fragment_registration) {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private val mAuth = FirebaseAuth.getInstance()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegistrationBinding.bind(view)
        if (mAuth.currentUser != null) {
            sendUserToHome()
        }
        binding.apply {
            registerButton.setOnClickListener {
                val email = emailTextView.text.toString()
                val password = passwordTextView.text.toString()
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(
                        context,
                        "Email and Password cannot be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(context, "Account Created", Toast.LENGTH_SHORT)
                                    .show()
                                if (mAuth.currentUser != null)
                                    FirebaseDatabase.getInstance().reference.child("Users")
                                        .child(mAuth.currentUser!!.uid).setValue("")
                                sendUserToHome()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Error: " + task.exception.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
            alreadyHaveAnAccountTextView.setOnClickListener {
                sendUserToLogin()
            }
        }
    }

    private fun sendUserToHome() {
        val action = RegistrationFragmentDirections.actionRegistrationFragmentToHomeFragment()
        findNavController().navigate(action)
    }

    private fun sendUserToLogin() {
        val action = RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment()
        findNavController().navigate(action)
    }
}