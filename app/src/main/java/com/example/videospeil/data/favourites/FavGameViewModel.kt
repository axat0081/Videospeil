package com.example.videospeil.data.favourites

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.videospeil.room.FavGame
import com.example.videospeil.room.FavGameDoa
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class FavGameViewModel @ViewModelInject constructor(
    private val favGameDoa: FavGameDoa
) : ViewModel() {

    private val favGameChannel = Channel<FavGameEvent>()
    val favEvent = favGameChannel.receiveAsFlow()
    val favourites = favGameDoa.getFavourites().asLiveData()
    fun onTaskSwiped(favGame:FavGame) = viewModelScope.launch {
        favGameDoa.delete(favGame)
        favGameChannel.send(FavGameEvent.ShouldUndoDeleteFavGame(favGame))
    }
    fun insert(favGame: FavGame) {
        viewModelScope.launch {
            favGameDoa.insert(favGame)
        }
    }

    fun onUndoGameDeleted(favGame: FavGame) = viewModelScope.launch {
        favGameDoa.insert(favGame)
    }


    sealed class FavGameEvent{
        data class ShouldUndoDeleteFavGame(val favGame: FavGame):FavGameEvent()
    }
}