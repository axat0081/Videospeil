package com.example.videospeil.data

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel

class GamesViewModel @ViewModelInject constructor(
    private val repository: GameRepository
):ViewModel() {
}