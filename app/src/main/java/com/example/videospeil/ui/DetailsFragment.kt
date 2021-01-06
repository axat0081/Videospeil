package com.example.videospeil.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailsBinding.bind(view)
        binding.apply {

        }
    }

    private fun addToFavourites(game: GameResults.Games) {
        viewModel.insert(FavGame(game.name!!))
    }
}