package com.example.kotlincats.data.api.catApi

import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {

    @GET("images/search")
    suspend fun getCats(
        @Query("limit") catsNumber: Int
    ): List<CatDto>
}