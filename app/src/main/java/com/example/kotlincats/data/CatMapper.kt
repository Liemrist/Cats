package com.example.kotlincats.data

import com.example.kotlincats.data.api.catApi.CatDto
import com.example.kotlincats.domain.model.Cat
import javax.inject.Inject

class CatMapper @Inject constructor() {

    fun mapCatsWithText(
        catDtos: List<CatDto>,
        hipsterText: List<String>
    ): List<Cat> {
        val cats: ArrayList<Cat> = arrayListOf()
        for ((index, cat) in catDtos.withIndex()) {
            val textFragments = hipsterText[index].split(" ", limit = 2)

            cats.add(
                Cat(
                    cat.hashCode(),
                    textFragments[0],
                    cat.imageUrl,
                    textFragments[1]
                )
            )
        }

        return cats
    }
}