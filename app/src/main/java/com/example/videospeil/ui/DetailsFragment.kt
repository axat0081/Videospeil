package com.example.videospeil.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailsBinding.bind(view)
        viewModel2.favourites.observe(viewLifecycleOwner) {
            for (g in it) {
                if(g.name == args.game.name){
                    binding.apply {
                        addToFavouritesButton.isVisible = false
                    }
                    break
                }
            }
        }
        val screenShotsAdapter = ScreenShotsAdapter()
        binding.apply {
            scrollView.scrollTo(0, 0)
            recyclerview.setHasFixedSize(true)
            recyclerview.adapter = screenShotsAdapter
            recyclerview.itemAnimator = null
            recyclerview.isNestedScrollingEnabled = false
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
                .placeholder(R.drawable.image_not_available)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.image_not_available)
                .into(gameImageView)

            addToFavouritesButton.setOnClickListener {
                addToFavourites(args.game)
            }
            val picList = args.game.picList
            picList.removeAt(0)
            screenShotsAdapter.submitList(picList)
            gameVideoView.setVideoPath(args.game.clip.video)
            playButton.setOnClickListener {
                if (gameVideoView.isPlaying) {
                    gameVideoView.pause()
                } else {
                    gameVideoView.start()
                }
            }

        }
    }

    private fun addToFavourites(game: GameResults.Games) {
        viewModel.insert(FavGame(game.name!!, game.imageUrl))
        binding.apply {
            addToFavouritesButton.isVisible = false
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}