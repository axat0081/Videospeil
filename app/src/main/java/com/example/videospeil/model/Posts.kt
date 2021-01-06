package com.example.videospeil.model

import java.text.DateFormat

data class Posts(
    val posterName: String,
    val message: String,
    val creationDate: String = DateFormat.getDateTimeInstance().format(System.currentTimeMillis())
) {
}