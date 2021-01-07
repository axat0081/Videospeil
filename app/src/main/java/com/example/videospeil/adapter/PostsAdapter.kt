package com.example.videospeil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.videospeil.databinding.PostsDisplayLayoutBinding
import com.example.videospeil.model.PostResults

class PostsAdapter :
    ListAdapter<PostResults.Posts, PostsAdapter.PostsViewHolder>(DiffCallBack()) {
    class DiffCallBack : DiffUtil.ItemCallback<PostResults.Posts>() {
        override fun areItemsTheSame(oldItem: PostResults.Posts, newItem: PostResults.Posts) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: PostResults.Posts, newItem: PostResults.Posts) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val binding =
            PostsDisplayLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item!!)
    }

    inner class PostsViewHolder(private val binding: PostsDisplayLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: PostResults.Posts) {
            binding.apply {
                posterNameTextView.text = post.posterName
                messageTextView.text = post.message
                creationDate.text = post.creationDate
            }
        }
    }
}