package com.example.kotlincats.api

import retrofit2.http.GET
import retrofit2.http.Query

interface CatServiceApi {

    @GET("images/search")
    suspend fun getCats(
        @Query("limit") limit: Int
    ): List<CatDto>
}