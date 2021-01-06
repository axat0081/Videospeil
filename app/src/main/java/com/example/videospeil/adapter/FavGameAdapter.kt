package com.example.videospeil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.videospeil.databinding.FavGameLayoutBinding
import com.example.videospeil.room.FavGame

class FavGameAdapter : ListAdapter<FavGame, FavGameAdapter.FavGameViewHolder>(DiffCallBack()) {
    class DiffCallBack : DiffUtil.ItemCallback<FavGame>() {
        override fun areItemsTheSame(oldItem: FavGame, newItem: FavGame) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: FavGame, newItem: FavGame) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavGameViewHolder {
        val binding =
            FavGameLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavGameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavGameViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class FavGameViewHolder(private val binding: FavGameLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favGame: FavGame) {
            binding.apply {
                favGameTextView.text = favGame.name
            }
        }
    }
}