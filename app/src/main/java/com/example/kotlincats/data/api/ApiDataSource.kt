package com.example.kotlincats.data.api

import com.example.kotlincats.data.models.CatApiModel

interface ApiDataSource {
    suspend fun getCats(quantity: Int): List<CatApiModel>
}