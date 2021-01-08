package com.example.videospeil.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "favourites")
@Parcelize
data class FavGame(
    val name: String,
    val image:String?,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable {

}