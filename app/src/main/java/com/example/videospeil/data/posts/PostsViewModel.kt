package com.example.videospeil.data.posts

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videospeil.model.PostResults
import kotlinx.coroutines.launch

class PostsViewModel @ViewModelInject constructor(
    private val repository: PostsRepository
) : ViewModel() {
    var posts = MutableLiveData<MutableList<PostResults.Posts>>()
    init {
        posts = repository.getPosts()
    }
    fun createPosts(posts: PostResults.Posts,context:Context) {
        viewModelScope.launch {
            repository.insertPosts(posts,context)
        }
    }
}