package com.example.kotlincats.data.api

import com.example.kotlincats.data.api.catApi.CatApi
import com.example.kotlincats.data.api.hipsterIpsumApi.HipsterIpsumApi
import com.example.kotlincats.data.models.CatApiModel
import com.example.kotlincats.util.ArrayUtil
import javax.inject.Inject

class ApiDataSourceImpl @Inject constructor(
    private val catApi: CatApi,
    private val hipsterIpsumApi: HipsterIpsumApi,
    private val arrayUtil: ArrayUtil
): ApiDataSource {

    override suspend fun getCats(quantity: Int): List<CatApiModel> {
        val catsFromApi = catApi.getCats(quantity)
        val text = hipsterIpsumApi.getParagraphs(quantity);

        return arrayUtil.transformIntoCatDataModel(catsFromApi, text)
    }
}