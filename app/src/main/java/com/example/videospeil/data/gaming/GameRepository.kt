package com.example.videospeil.data.gaming

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.videospeil.api.Api
import javax.inject.Inject

class GameRepository @Inject constructor(private val api: Api) {
    fun getGames(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 30,
                enablePlaceholders = false

            ),
            pagingSourceFactory = { GamingPagingSource(api, query) }
        ).liveData
}