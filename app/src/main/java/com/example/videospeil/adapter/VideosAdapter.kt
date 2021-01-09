package com.example.videospeil.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.videospeil.databinding.VideoDisplayLayoutBinding
import com.example.videospeil.model.Videos

class VideosAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Videos, VideosAdapter.VideosViewHolder>(DiffCallBack()) {

    class DiffCallBack : DiffUtil.ItemCallback<Videos>() {
        override fun areItemsTheSame(oldItem: Videos, newItem: Videos) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Videos, newItem: Videos) =
            oldItem.url == newItem.url
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosViewHolder {
        val binding =
            VideoDisplayLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideosViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    interface OnItemClickListener {
        fun onItemCLick(video: Videos)
    }

    inner class VideosViewHolder(private val binding: VideoDisplayLayoutBinding) :
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

        fun bind(item: Videos) {
            binding.apply {
                Glide.with(itemView)
                    .load(item.url)
                    .centerCrop()
                    .fitCenter()
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
                    .into(thumbnailImageView)
                addedTextView.text = item.addedOn
                videoTitleTextView.text = item.title
            }
        }

    }
}