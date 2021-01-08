package com.example.videospeil.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.videospeil.R
import com.example.videospeil.databinding.ScreenshotsBinding
import com.example.videospeil.model.GameResults

class ScreenShotsAdapter :
    ListAdapter<GameResults.Games.ScreensShots, ScreenShotsAdapter.ScreenshotViewHolder>(DiffCallBack()) {

    class DiffCallBack() : DiffUtil.ItemCallback<GameResults.Games.ScreensShots>() {
        override fun areItemsTheSame(oldItem: GameResults.Games.ScreensShots, newItem: GameResults.Games.ScreensShots) =
            oldItem.image == newItem.image

        override fun areContentsTheSame(oldItem: GameResults.Games.ScreensShots, newItem: GameResults.Games.ScreensShots) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenshotViewHolder {
        val binding = ScreenshotsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScreenshotViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScreenshotViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ScreenshotViewHolder(private val binding: ScreenshotsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(screensShots: GameResults.Games.ScreensShots) {
            binding.apply {
                Glide.with(itemView)
                    .load(screensShots.image)
                    .centerCrop()
                    .fitCenter()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .placeholder(R.drawable.image_not_available)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.image_not_available)
                    .into(screenshotImageView)
            }
        }

    }
}