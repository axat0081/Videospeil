package com.example.videospeil.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.videospeil.R
import com.example.videospeil.api.postsApi
import com.example.videospeil.databinding.FragmentCreatePostsBinding
import com.example.videospeil.model.PostResults
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@AndroidEntryPoint
class CreatePostsFragment : Fragment(R.layout.fragment_create_posts) {
    private var _binding: FragmentCreatePostsBinding? = null
    private val binding get() = _binding!!
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
                    posterNameTextView.text.clear()
                    messageTextView.text.clear()
                    val post = PostResults.Posts(posterName = name, message = message)
                    val retrofit = Retrofit.Builder()
                        .baseUrl(postsApi.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    val api = retrofit.create(postsApi::class.java)
                    val call = api.insertPost(post)
                    call.enqueue(object : Callback<PostResults> {
                        override fun onFailure(call: Call<PostResults>, t: Throwable) {
                            Toast.makeText(
                                context,
                                "Error: " + t.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onResponse(
                            call: Call<PostResults>,
                            response: Response<PostResults>
                        ) {
                            if (!response.isSuccessful) {
                                Toast.makeText(
                                    context,
                                    "Error: " + response.code().toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                                return
                            } else {
                                Toast.makeText(context, "Post Sent", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
                }
            }
        }
    }
}