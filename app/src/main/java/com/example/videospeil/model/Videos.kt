package com.example.videospeil.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat

@Parcelize
data class Videos(
    val userId: String,
    val uploader: String,
    val url: String,
    val title: String,
    val addedOn: String = DateFormat.getDateTimeInstance()
        .format(System.currentTimeMillis())
) : Parcelable