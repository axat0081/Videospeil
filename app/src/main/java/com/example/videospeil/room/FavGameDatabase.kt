package com.example.videospeil.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider


@Database(entities = [FavGame::class], version = 3)
abstract class FavGameDatabase : RoomDatabase() {
    abstract fun favGameDao(): FavGameDoa
    class Callback @Inject constructor(
        private val database: Provider<FavGameDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val dao = database.get().favGameDao()
            applicationScope.launch {
                dao.insert(FavGame("Your Favourites will appear here"))
                dao.insert(FavGame("No Favourites yet"))
            }
        }
    }

}