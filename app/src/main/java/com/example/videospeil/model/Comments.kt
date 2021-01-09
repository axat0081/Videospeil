package com.example.videospeil.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comments(
    val commenterName: String,
    val comment: String
) :Parcelable