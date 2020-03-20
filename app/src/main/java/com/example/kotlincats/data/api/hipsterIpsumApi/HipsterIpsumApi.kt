package com.example.kotlincats.data.api.hipsterIpsumApi

import retrofit2.http.GET
import retrofit2.http.Query

interface HipsterIpsumApi {

    @GET("https://hipsum.co/api/?type=hipster-centric")
    suspend fun getParagraphs(
        @Query("paras") paragraphsNumber: Int
    ): List<String>
}