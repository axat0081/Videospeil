package com.example.videospeil.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.videospeil.R
import com.example.videospeil.data.GamesViewModel
import com.example.videospeil.databinding.FragmentGamesListBinding
import com.example.videospeil.model.GameResults
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GamesListFragment : Fragment(R.layout.fragment_games_list) {
    private var _binding: FragmentGamesListBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<GamesViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGamesListBinding.bind(view)
        binding.apply {

        }
    }
    fun sendUserToDetails(game:GameResults.Games){
        val action = GamesListFragmentDirections.actionGamesListFragmentToDetailsFragment(game)
        findNavController().navigate(action)
    }
}