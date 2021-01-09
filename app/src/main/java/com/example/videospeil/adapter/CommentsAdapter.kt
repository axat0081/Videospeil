package com.example.videospeil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.videospeil.databinding.CommentsDisplayLayoutBinding
import com.example.videospeil.model.Comments

class CommentsAdapter(private val list: ArrayList<Comments>) :
    RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {

    class DiffCallback() : DiffUtil.ItemCallback<Comments>() {
        override fun areItemsTheSame(oldItem: Comments, newItem: Comments) =
            oldItem.commenterName == newItem.commenterName

        override fun areContentsTheSame(oldItem: Comments, newItem: Comments) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val binding =
            CommentsDisplayLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount() = list.size

    inner class CommentsViewHolder(private val binding: CommentsDisplayLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comments) {
            binding.apply {
                commenterName.text = comment.commenterName
                commentMessage.text = comment.comment
            }
        }
    }
}