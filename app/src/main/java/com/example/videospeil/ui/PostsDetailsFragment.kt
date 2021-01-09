package com.example.videospeil.ui

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.example.videospeil.R
import com.example.videospeil.adapter.CommentsAdapter
import com.example.videospeil.data.posts.PostsViewModel
import com.example.videospeil.databinding.FragmentPostsDetailsBinding
import com.example.videospeil.model.Comments
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsDetailsFragment : Fragment(R.layout.fragment_posts_details) {
    private var _binding: FragmentPostsDetailsBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<PostsDetailsFragmentArgs>()
    private val viewModel by viewModels<PostsViewModel>()
    private val list = ArrayList<Comments>()
    private val commentsAdapter = CommentsAdapter(list)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPostsDetailsBinding.bind(view)
        commentsAdapter.notifyDataSetChanged()
        binding.apply {
            posterNameTextView.text = args.post.posterName
            messageTextView.text = args.post.message
            recyclerview.apply {
                adapter = commentsAdapter
                setHasFixedSize(true)
                itemAnimator = null
            }
            createdTextView.text = args.post.creationDate
            createCommentTextView.setOnClickListener {
                createComment()
            }
            getComments(args.post.id)
        }
        FirebaseDatabase.getInstance().reference.child("posts").child(args.post.id)
            .child("commentsList").addChildEventListener(object : ChildEventListener {

                override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                    if (dataSnapshot.value != null) {
                        val name = dataSnapshot.child("commenterName").value.toString()
                        val comment = dataSnapshot.child("comment").value.toString()
                        list.add(Comments(name, comment))
                    }
                    commentsAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    TODO("Not yet implemented")
                }
            })
        //setHasOptionsMenu(true)
    }

    private fun getComments(id: String) {
        viewModel.getComments(id)
    }

    private fun createComment() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Comment")
        builder.setCancelable(false)
        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.input_comment_layout, null)
        val commenterNameEditText:EditText = view.findViewById(R.id.commenter_name)
        val commentEditText:EditText = view.findViewById(R.id.comment_edit_text)
        builder.setView(view)
        builder.setPositiveButton(
            "Comment"
        ) { dialog, _ ->
            val name = commenterNameEditText.text.toString()
            val comment = commentEditText.text.toString()
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(comment)) {
                Toast.makeText(context, "Name and Comment cannot be empty", Toast.LENGTH_SHORT)
                    .show()
                dialog.cancel()
            } else {
                val newComment = Comments(name, comment)
                viewModel.createComment(
                    id = args.post.id,
                    comments = Comments(name, comment),
                    context = requireContext()
                )

            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.comment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_comment) {
            createComment()
        }
        return true
    }
}