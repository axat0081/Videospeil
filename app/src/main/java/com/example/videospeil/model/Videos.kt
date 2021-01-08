package com.example.videospeil.model

import java.text.DateFormat

data class Videos(
    val uploader:String,
    val url:String,
    val addedOn:String = DateFormat.getDateTimeInstance()
        .format(System.currentTimeMillis())
)