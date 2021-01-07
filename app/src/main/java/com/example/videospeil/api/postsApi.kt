package com.example.videospeil.api

import com.example.videospeil.model.PostResults
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface postsApi {
    companion object {
        const val BASE_URL = "https://videospiel.herokuapp.com/"
    }

    @GET("lang")
    fun getPosts(): Call<PostResults>

    @POST("lang")
    fun insertPost(@Body post: PostResults.Posts): Call<PostResults>
}