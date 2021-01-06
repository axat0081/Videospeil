package com.example.videospeil.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.videospeil.R
import com.example.videospeil.databinding.FragmentPostsLayoutBinding

class PostsFragment : Fragment(R.layout.fragment_posts_layout) {
    private var _binding: FragmentPostsLayoutBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}