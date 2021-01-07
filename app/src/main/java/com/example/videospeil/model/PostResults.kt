package com.example.videospeil.model

import com.google.gson.annotations.SerializedName
import java.text.DateFormat

data class PostResults(
    @SerializedName("posts")
    val postsList: List<Posts>
) {
    data class Posts(
        val posterName: String,
        val message: String,
        val creationDate: String = DateFormat.getDateTimeInstance()
            .format(System.currentTimeMillis())
    ) {
    }
}