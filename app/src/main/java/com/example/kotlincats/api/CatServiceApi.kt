package com.example.kotlincats.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CatServiceApi {

    @GET("images/search")
    fun getCats(
        @Query("limit") limit: Int
    ): Call<List<CatDto>>
}