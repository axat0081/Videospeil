package com.example.videospeil.api

import com.example.videospeil.model.GameResults
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    companion object {
        const val BASE_URL = "https://api.rawg.io/api/"
    }

    @GET("games")
    suspend fun getGames(
        @Query("page") page: String,
    ): GameResults
}