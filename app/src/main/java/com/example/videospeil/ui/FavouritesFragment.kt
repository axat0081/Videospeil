package com.example.videospeil.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.videospeil.R
import com.example.videospeil.adapter.FavGameAdapter
import com.example.videospeil.data.favourites.FavGameViewModel
import com.example.videospeil.databinding.FragmentFavouritesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favourites.*

@AndroidEntryPoint
class FavouritesFragment : Fragment(R.layout.fragment_favourites) {
    private val viewModel by viewModels<FavGameViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFavouritesBinding.bind(view)
        val favAdapter = FavGameAdapter()
        binding.apply {
            recyclerview.apply {
                itemAnimator = null
                adapter = favAdapter
                setHasFixedSize(true)
            }
        }
        viewModel.favourites.observe(viewLifecycleOwner) {
            favAdapter.submitList(it)
        }
    }
}