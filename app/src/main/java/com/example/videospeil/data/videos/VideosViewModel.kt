package com.example.videospeil.data.videos

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel

class VideosViewModel @ViewModelInject constructor(
 private val repository:VideosRepository
):ViewModel() {

}