package com.example.videospeil.model

import android.os.Parcelable
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat

data class PostResults(
    @SerializedName("posts")
    val postsList: List<Posts>
) {
    @Parcelize
    data class Posts(
        var id: String,
        val posterId:String,
        val posterName: String,
        val message: String,
        val creationDate: String = DateFormat.getDateTimeInstance()
            .format(System.currentTimeMillis()),
        val commentsList: ArrayList<Comments>
    ) : Parcelable
}