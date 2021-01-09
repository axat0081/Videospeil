package com.example.videospeil.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.videospeil.R
import com.example.videospeil.data.posts.PostsViewModel
import com.example.videospeil.databinding.FragmentCreatePostsBinding
import com.example.videospeil.model.Comments
import com.example.videospeil.model.PostResults
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatePostsFragment : Fragment(R.layout.fragment_create_posts) {
    private var _binding: FragmentCreatePostsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<PostsViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCreatePostsBinding.bind(view)
        binding.apply {
            createPostsButton.setOnClickListener {
                val name = posterNameTextView.text.toString()
                val message = messageTextView.text.toString()
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(message)) {
                    Toast.makeText(context, "Name or message cannot be empty", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val key = FirebaseDatabase.getInstance().reference.child("posts").push().key
                    posterNameTextView.text.clear()
                    messageTextView.text.clear()
                    val list = ArrayList<Comments>()
                    list.add(Comments(commenterName = "Dev Team", comment = "Enter comments here"))
                    val post = PostResults.Posts(id = key!!,posterName = name, message = message, commentsList = list)
                    viewModel.createPosts(post, requireContext())
                }
            }
        }
    }
}