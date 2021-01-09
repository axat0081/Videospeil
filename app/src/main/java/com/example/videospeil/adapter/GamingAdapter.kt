package com.example.videospeil.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.videospeil.R
import com.example.videospeil.databinding.GamesDisplayLayoutBinding
import com.example.videospeil.model.GameResults

class GamingAdapter(
    private val listener: OnItemClickListener
) : PagingDataAdapter<GameResults.Games, GamingAdapter.GamesViewHolder>(GAME_COMPARATOR) {

    companion object {
        private val GAME_COMPARATOR = object : DiffUtil.ItemCallback<GameResults.Games>() {
            override fun areItemsTheSame(
                oldItem: GameResults.Games,
                newItem: GameResults.Games
            ) = (oldItem.name == newItem.name)

            override fun areContentsTheSame(
                oldItem: GameResults.Games,
                newItem: GameResults.Games
            ) = oldItem == newItem

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewHolder {
        val binding =
            GamesDisplayLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GamesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    interface OnItemClickListener {
        fun onItemCLick(game: GameResults.Games)
    }

    inner class GamesViewHolder(private val binding: GamesDisplayLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemCLick(item)
                    }
                }
            }
        }

        fun bind(games: GameResults.Games) {
            binding.apply {
                if (games.imageUrl == null) {
                    Glide.with(itemView)
                        .load(R.drawable.image_not_available)
                        .centerCrop()
                        .fitCenter()
                        .placeholder(R.drawable.image_not_available)
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageView)
                } else {
                    Glide.with(itemView)
                        .load(games.imageUrl)
                        .placeholder(R.drawable.image_not_available)
                        .centerCrop()
                        .fitCenter()
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                progressBar.isVisible = false

                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                progressBar.isVisible = false
                                return false
                            }
                        })
                        .into(imageView)
                }
                textView.text = games.name
            }
        }
    }
}