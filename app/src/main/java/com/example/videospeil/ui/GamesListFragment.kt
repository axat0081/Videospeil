package com.example.videospeil.ui

import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.videospeil.R
import com.example.videospeil.adapter.GamesLoadStateAdapter
import com.example.videospeil.adapter.GamingAdapter
import com.example.videospeil.data.gaming.GamesViewModel
import com.example.videospeil.databinding.FragmentGamesListBinding
import com.example.videospeil.model.GameResults
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GamesListFragment : Fragment(R.layout.fragment_games_list),
    GamingAdapter.OnItemClickListener {
    private var _binding: FragmentGamesListBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<GamesViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGamesListBinding.bind(view)
        val adapter = GamingAdapter(this)
        binding.apply {
            recyclerview.setHasFixedSize(true)
            recyclerview.itemAnimator = null
            recyclerview.adapter = adapter.withLoadStateHeaderAndFooter(
                header = GamesLoadStateAdapter { adapter.retry() },
                footer = GamesLoadStateAdapter { adapter.retry() }
            )
            retryBtn.setOnClickListener {
                adapter.retry()
            }
        }
        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerview.isVisible = loadState.source.refresh !is LoadState.Loading
                retryBtn.isVisible = loadState.source.refresh is LoadState.Error
                txtError.isVisible = loadState.source.refresh is LoadState.Error
                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    recyclerview.isVisible = false
                    noResultsTxt.isVisible = true
                } else {
                    noResultsTxt.isVisible = false
                }
            }
        }
        viewModel.games.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.inputType = InputType.TYPE_CLASS_NUMBER
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    val num: Long = query.toLong()
                    if (num > 7) {
                        Toast.makeText(
                            context,
                            "This page number is not available ",
                            Toast.LENGTH_LONG
                        ).show()
                        return true
                    }
                    binding.recyclerview.scrollToPosition(0)
                    viewModel.search(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onItemCLick(game: GameResults.Games) {
        sendUserToDetails(game)
    }

    private fun sendUserToDetails(game: GameResults.Games) {
        val action = GamesListFragmentDirections.actionGamesListFragmentToDetailsFragment(game)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}