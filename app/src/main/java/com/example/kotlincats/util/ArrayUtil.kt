package com.example.kotlincats.util

import com.example.kotlincats.data.api.catApi.CatDto
import com.example.kotlincats.data.models.CatApiModel
import javax.inject.Inject


class ArrayUtil @Inject constructor() {

    /**
     * A function to transform the CatDto and a Hipster text from server to a CatApiModel.
     */
    fun transformIntoCatDataModel(
        catDtos: List<CatDto>,
        hipsterText: List<String>
    ): List<CatApiModel> {
        val cats: ArrayList<CatApiModel> = arrayListOf()

        for ((index, cat) in catDtos.withIndex()) {
            val textFragments = hipsterText[index].split(" ", limit = 2)

            cats.add(CatApiModel(textFragments[0], cat.imageUrl, textFragments[1]))
        }

        return cats
    }
}