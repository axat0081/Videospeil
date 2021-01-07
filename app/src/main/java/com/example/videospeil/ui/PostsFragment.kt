package com.example.videospeil.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.videospeil.R
import com.example.videospeil.adapter.PostsAdapter
import com.example.videospeil.api.postsApi
import com.example.videospeil.databinding.FragmentPostsLayoutBinding
import com.example.videospeil.model.PostResults
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@AndroidEntryPoint
class PostsFragment : Fragment(R.layout.fragment_posts_layout) {
    private var _binding: FragmentPostsLayoutBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPostsLayoutBinding.bind(view)
        val postsAdapter = PostsAdapter()
        val retrofit = Retrofit.Builder()
            .baseUrl(postsApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(postsApi::class.java)
        binding.apply {
            recyclerview.apply {
                setHasFixedSize(true)
                itemAnimator = null
                adapter = postsAdapter
                progressBar.isVisible = true
            }
        }
        val call = api.getPosts()
        call.enqueue(object : Callback<PostResults> {

            override fun onFailure(call: Call<PostResults>, t: Throwable) {
                binding.apply {
                    progressBar.isVisible = false
                }
                Toast.makeText(context, "Error: " + t.message.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PostResults>, response: Response<PostResults>) {
                binding.apply {
                    progressBar.isVisible = false
                }
                if (!response.isSuccessful) {
                    Toast.makeText(
                        context,
                        "Error: " + response.code().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                } else {
                    val list = response.body()!!.postsList
                    postsAdapter.submitList(list)
                    scroll(list.size - 1)
                }
            }
        })
    }

    private fun scroll(t: Int) {
        binding.apply {
            recyclerview.smoothScrollToPosition(t)
        }
    }
}