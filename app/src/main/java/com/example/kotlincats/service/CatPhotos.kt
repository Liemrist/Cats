package com.example.kotlincats.service

import com.example.kotlincats.api.CatResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object CatPhotos {

    private val catServiceApi: CatServiceApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        catServiceApi = retrofit.create()
    }

    fun getCats(): Call<List<CatResponse>> = catServiceApi.getCats()
}