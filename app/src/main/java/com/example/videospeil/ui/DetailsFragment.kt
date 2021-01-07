package com.example.videospeil.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.videospeil.R
import com.example.videospeil.data.favourites.FavGameViewModel
import com.example.videospeil.databinding.FragmentDetailsBinding
import com.example.videospeil.model.GameResults
import com.example.videospeil.room.FavGame
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<FavGameViewModel>()
    private val args by navArgs<DetailsFragmentArgs>()
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailsBinding.bind(view)
        binding.apply {
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
        }
    }

    private fun addToFavourites(game: GameResults.Games) {
        viewModel.insert(FavGame(game.name!!))
    }
}