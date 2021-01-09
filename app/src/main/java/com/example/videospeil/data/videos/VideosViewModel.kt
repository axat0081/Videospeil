package com.example.videospeil.data.videos

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.videospeil.model.Videos

class VideosViewModel @ViewModelInject constructor(
    private val repository: VideosRepository
) : ViewModel() {
    var videosList = MutableLiveData<MutableList<Videos>>()

    init {
        videosList = repository.getVideos()
    }

}