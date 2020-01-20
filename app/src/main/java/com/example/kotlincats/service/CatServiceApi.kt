package com.example.kotlincats.service

import com.example.kotlincats.api.CatResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CatServiceApi {
    @GET("images/search")
    fun getCats(
        @Query("limit") limit: Int
    ): Call<List<CatResponse>>
}