package com.example.videospeil.model

import android.net.Uri
import java.text.DateFormat

data class Videos(
    val uploader: String,
    val url: Uri?,
    val addedOn: String = DateFormat.getDateTimeInstance()
        .format(System.currentTimeMillis())
)