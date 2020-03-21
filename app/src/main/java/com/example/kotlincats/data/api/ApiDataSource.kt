package com.example.kotlincats.data.api

import com.example.kotlincats.data.CatMapper
import com.example.kotlincats.data.api.catApi.CatApi
import com.example.kotlincats.data.api.hipsterIpsumApi.HipsterIpsumApi
import com.example.kotlincats.domain.model.Cat
import javax.inject.Inject

class ApiDataSource @Inject constructor(
    private val catApi: CatApi,
    private val hipsterIpsumApi: HipsterIpsumApi,
    private val catMapper: CatMapper
) {

    suspend fun getCats(quantity: Int): List<Cat> {
        val catsFromApi = catApi.getCats(quantity)
        val text = hipsterIpsumApi.getParagraphs(quantity);

        return catMapper.mapCatsWithText(catsFromApi, text)
    }
}