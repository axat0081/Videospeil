package com.example.videospeil.data.gaming

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.videospeil.data.gaming.GameRepository

class GamesViewModel @ViewModelInject constructor(
    private val repository: GameRepository
) : ViewModel() {
    companion object {
        const val QUERY = "1"
    }

    private val dataQuery = MutableLiveData(QUERY)
    val games = dataQuery.switchMap {
        repository.getGames(it).cachedIn(viewModelScope)
    }

    fun search(query: String) {
        dataQuery.value = query
    }
}