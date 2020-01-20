package com.example.kotlincats.service

import com.example.kotlincats.api.CatResponse
import retrofit2.Call
import retrofit2.http.GET

interface CatServiceApi {
    @GET("images/search?limit=5") // TODO: make limit a parameter.
    fun getCats(): Call<List<CatResponse>>
}