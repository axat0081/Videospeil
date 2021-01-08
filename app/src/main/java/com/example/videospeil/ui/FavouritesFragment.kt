package com.example.videospeil.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.videospeil.R
import com.example.videospeil.adapter.FavGameAdapter
import com.example.videospeil.data.favourites.FavGameViewModel
import com.example.videospeil.databinding.FragmentFavouritesBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favourites.*
import kotlinx.coroutines.flow.collect

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
            ItemTouchHelper(object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ) = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val fav = favAdapter.currentList[viewHolder.adapterPosition]
                    viewModel.onTaskSwiped(fav)
                }
            }).attachToRecyclerView(recyclerview)
        }
        viewModel.favourites.observe(viewLifecycleOwner) {
            favAdapter.submitList(it)

            binding.apply {
                emptyTextView.isVisible = it.isEmpty()
                empty2TextView.isVisible = it.isEmpty()

            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.favEvent.collect { event ->
                when (event) {
                    is FavGameViewModel.FavGameEvent.ShouldUndoDeleteFavGame -> {
                        Snackbar.make(
                            requireView(),
                            "Deleted From Favourites",
                            Snackbar.LENGTH_LONG
                        )
                            .setAction("UNDO") {
                                viewModel.onUndoGameDeleted(event.favGame)
                            }.show()
                    }
                }

            }
        }
    }
}