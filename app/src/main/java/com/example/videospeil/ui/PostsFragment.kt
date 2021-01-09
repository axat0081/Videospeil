package com.example.videospeil.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.example.videospeil.R
import com.example.videospeil.adapter.PostsAdapter
import com.example.videospeil.data.posts.PostsViewModel
import com.example.videospeil.databinding.FragmentPostsLayoutBinding
import com.example.videospeil.model.PostResults
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsFragment : Fragment(R.layout.fragment_posts_layout), PostsAdapter.OnItemClickListener {
    private var _binding: FragmentPostsLayoutBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<PostsViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPostsLayoutBinding.bind(view)
        val postsAdapter = PostsAdapter(this)
        binding.apply {
            recyclerview.apply {
                setHasFixedSize(true)
                itemAnimator = null
                adapter = postsAdapter
                progressBar.isVisible = true
            }
        }
        viewModel.posts.observe(viewLifecycleOwner) {
            postsAdapter.submitList(it as List<PostResults.Posts>)
            binding.apply {
                progressBar.isVisible = it.isEmpty()
            }
            scroll(it.size - 1)
        }
    }

    private fun scroll(t: Int) {
        binding.apply {
            if (t > 1)
                recyclerview.smoothScrollToPosition(t)
        }
    }

    override fun onItemCLick(post: PostResults.Posts) {
        sendUserToPostDetails(post)
    }

    private fun sendUserToPostDetails(post: PostResults.Posts) {
        assert(post !=null)
        val action = PostsFragmentDirections.actionPostsFragmentToPostsDetailsFragment(post)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}