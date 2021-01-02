package com.example.videospeil.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameResults(
    var results: List<Games>
) : Parcelable {
    @Parcelize
    data class Games(
        var name: String?,
        @SerializedName("background_image")
        var imageUrl: String?,
        var rating: String?,
        var genres: List<Genres>
    ) : Parcelable {
        @Parcelize
        data class Genres(
            var name: String?
        ) : Parcelable
    }
}