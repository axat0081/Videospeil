package com.example.videospeil.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.videospeil.R
import com.example.videospeil.adapter.PostsAdapter
import com.example.videospeil.data.posts.PostsViewModel
import com.example.videospeil.databinding.FragmentPostsLayoutBinding
import com.example.videospeil.model.PostResults
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsFragment : Fragment(R.layout.fragment_posts_layout) {
    private var _binding: FragmentPostsLayoutBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<PostsViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPostsLayoutBinding.bind(view)
        val postsAdapter = PostsAdapter()
        binding.apply {
            recyclerview.apply {
                setHasFixedSize(true)
                itemAnimator = null
                adapter = postsAdapter
                progressBar.isVisible = true
            }
        }
   /*     val list = ArrayList<PostResults.Posts>()
        FirebaseDatabase.getInstance().reference.child("posts")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val set = HashSet<PostResults.Posts>()
                    for(snapshot in dataSnapshot.children){
                        set.add(
                            PostResults.Posts(
                                posterName = snapshot.child("posterName").key.toString(),
                                message = snapshot.child("message").key.toString(),
                                creationDate = snapshot.child("creationDate").key.toString()
                            )
                        )
                    }
                    list.clear()
                    list.addAll(set)
                }
            })
        postsAdapter.submitList(list)
        scroll(list.size-1)*/
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
}