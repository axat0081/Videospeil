package com.example.videospeil.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavGameDoa {
    @Query("SELECT * from favourites")
    fun getFavourites(): Flow<List<FavGame>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favGame: FavGame)

    @Delete
    suspend fun delete(favGame: FavGame)
}