package com.example.videospeil.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.videospeil.R
import com.example.videospeil.databinding.FragmentCreatePostsBinding

class CreatePostsFragment : Fragment(R.layout.fragment_create_posts) {
    private var _binding: FragmentCreatePostsBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCreatePostsBinding.bind(view)
        binding.apply {
            createPostsButton.setOnClickListener {
                val name = posterNameTextView.text
                val message = messageTextView.text
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(message)) {
                    Toast.makeText(context, "Name or message cannot be empty", Toast.LENGTH_SHORT)
                        .show()
                } else {

                }
            }
        }
    }
}