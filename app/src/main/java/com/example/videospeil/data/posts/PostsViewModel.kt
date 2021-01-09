package com.example.videospeil.data.posts

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.videospeil.model.Comments
import com.example.videospeil.model.PostResults
import kotlinx.coroutines.launch

class PostsViewModel @ViewModelInject constructor(
    private val repository: PostsRepository
) : ViewModel() {

    companion object {
        const val id = "-MQaiT7EPkGaPj8vtbA_"
        const val num = -1
    }

    private val commentId = MutableLiveData(id)
    private val numId = MutableLiveData(num)

    private val combinedValues = MediatorLiveData<Pair<String?, Int?>>().apply {
        addSource(commentId) {
            value = Pair(it, numId.value)
        }
        addSource(numId) {
            value = Pair(commentId.value, it)
        }
    }

    val commentsList = combinedValues.switchMap {
        repository.getCommentList(it.first!!)
    }


    var posts = MutableLiveData<MutableList<PostResults.Posts>>()

    init {
        posts = repository.getPosts()
    }

    fun createPosts(posts: PostResults.Posts, context: Context) {
        viewModelScope.launch {
            repository.insertPosts(posts, context)
        }
    }

    fun createComment(id: String, comments: Comments, context: Context,posterId : String) {
        viewModelScope.launch {
            repository.insertComment(id, comments, context,posterId)
        }
        val k = numId.value
        numId.value = k!! + 1
    }

    fun getComments(id: String) {
        commentId.value = id
    }
}