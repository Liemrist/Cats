package com.example.kotlincats.data.api.hipsterIpsumApi

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface HipsterIpsumApi {

    @GET("https://hipsum.co/api/?type=hipster-centric")
    fun getParagraphs(
        @Query("paras") paragraphsNumber: Int
    ): Single<List<String>>
}