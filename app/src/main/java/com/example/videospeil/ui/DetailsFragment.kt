package com.example.videospeil.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.MediaController
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.videospeil.R
import com.example.videospeil.adapter.ScreenShotsAdapter
import com.example.videospeil.data.favourites.FavGameViewModel
import com.example.videospeil.databinding.FragmentDetailsBinding
import com.example.videospeil.model.GameResults
import com.example.videospeil.model.PostResults
import com.example.videospeil.room.FavGame
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<FavGameViewModel>()
    private val args by navArgs<DetailsFragmentArgs>()
    private val viewModel2 by viewModels<FavGameViewModel>()
    private val message =
        "Added to favourites. You can go to favourites list to remove from favourites"

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailsBinding.bind(view)
        viewModel2.favourites.observe(viewLifecycleOwner) {
            for (g in it) {
                if (g.name == args.game.name) {
                    binding.apply {
                        addToFavouritesButton.isClickable = false
                        addToFavouritesButton.text = message
                        addToFavouritesButton.textSize = 10F
                    }
                    break
                }
            }
        }
        val screenShotsAdapter = ScreenShotsAdapter()
        val controller = MediaController(context)
        controller.setAnchorView(binding.gameVideoView)
        binding.gameVideoView.setMediaController(controller)
        binding.apply {
            scrollView.scrollTo(0, 0)
            recyclerview.apply {
                setHasFixedSize(true)
                adapter = screenShotsAdapter
                itemAnimator = null
                isNestedScrollingEnabled = false
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
            textView.text = args.game.name
            var result: String = "Genre: "
            for (g in args.game.genres) {
                result += " " + g.name
            }
            textView2.text = result
            textView3.text = "Rating:- " + args.game.rating
            Glide.with(requireContext())
                .load(args.game.imageUrl)
                .centerCrop()
                .fitCenter()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.image_not_available)
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
                .into(gameImageView)

            addToFavouritesButton.setOnClickListener {
                addToFavourites(args.game)
            }
            val picList = args.game.picList
            picList.removeAt(0)
            screenShotsAdapter.submitList(picList)
            gameVideoView.setVideoPath(args.game.clip.video)
            gameVideoView.setOnPreparedListener { mp ->
                videoProgressBar.isVisible = false
                mp.setOnVideoSizeChangedListener { mp, _, _ ->
                    mp.start()
                }
            }

        }
    }

    private fun addToFavourites(game: GameResults.Games) {
        viewModel.insert(FavGame(game.name!!, game.imageUrl))
        binding.apply {
            addToFavouritesButton.isClickable = false
            addToFavouritesButton.text = message
            addToFavouritesButton.textSize = 10F
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}