package com.example.videospeil.data.favourites

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.videospeil.room.FavGame
import com.example.videospeil.room.FavGameDoa
import kotlinx.coroutines.launch

class FavGameViewModel @ViewModelInject constructor(
    private val favGameDoa: FavGameDoa
) : ViewModel() {

    val favourites = favGameDoa.getFavourites().asLiveData()

    fun insert(favGame: FavGame) {
        viewModelScope.launch {
            favGameDoa.insert(favGame)
        }
    }
}