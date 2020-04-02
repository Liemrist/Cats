package com.example.kotlincats.data.api.catApi

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {

    @GET("images/search")
    fun getCats(
        @Query("limit") catsNumber: Int
    ): Single<List<CatDto>>
}