package com.example.kotlincats.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CatDto (
    @Json(name = "id")
    val id: String,
    @Json(name = "url")
    val imageUrl: String,
    @Json(name = "width")
    val width: Int,
    @Json(name = "height")
    val height: Int
)