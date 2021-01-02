package com.example.videospeil.data

import androidx.paging.PagingSource
import com.example.videospeil.api.Api
import com.example.videospeil.model.GameResults
import retrofit2.HttpException
import java.io.IOException

class GamingPagingSource(
    private val api: Api,
    private val query: String
) : PagingSource<Int, GameResults.Games>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GameResults.Games> {
        val position = params.key ?: 1
        return try {
            val response:GameResults = api.getGames(page = query)
            val gameList = response.results
            LoadResult.Page(
                data = gameList,
                prevKey = if (position == 1) null else position + 1,
                nextKey = if (gameList.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}