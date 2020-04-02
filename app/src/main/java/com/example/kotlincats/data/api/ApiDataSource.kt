package com.example.kotlincats.data.api

import com.example.kotlincats.data.CatMapper
import com.example.kotlincats.data.api.catApi.CatApi
import com.example.kotlincats.data.api.catApi.CatDto
import com.example.kotlincats.data.api.hipsterIpsumApi.HipsterIpsumApi
import com.example.kotlincats.domain.model.Cat
import io.reactivex.Single
import javax.inject.Inject

class ApiDataSource @Inject constructor(
    private val catApi: CatApi,
    private val hipsterIpsumApi: HipsterIpsumApi,
    private val catMapper: CatMapper
) {

    fun getCats(quantity: Int): Single<List<Cat>> {
        val catsFromApi = catApi.getCats(quantity)
        return catsFromApi.flatMap { cats -> mapCatsToCatModel(cats, quantity) }
    }


    private fun mapCatsToCatModel(cats: List<CatDto>, quantity: Int): Single<List<Cat>> {
        val text = hipsterIpsumApi.getParagraphs(quantity)
        return text.map { item -> catMapper.mapCatsWithText(cats, item) }
    }
}