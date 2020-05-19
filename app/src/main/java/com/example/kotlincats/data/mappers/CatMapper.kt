package com.example.kotlincats.data.mappers

import com.example.kotlincats.data.models.CatApiModel
import com.example.kotlincats.data.models.CatDatabaseModel
import com.example.kotlincats.domain.models.Cat
import javax.inject.Inject

class CatMapper @Inject constructor() {

    fun toCat(catApiModel: CatApiModel): Cat {
        return Cat(
            catApiModel.hashCode(),
            catApiModel.name,
            catApiModel.imageUrl,
            catApiModel.info
        )
    }

    fun toCatDatabaseModel(cat: Cat): CatDatabaseModel {
        return CatDatabaseModel(
            cat.id,
            cat.name,
            cat.photoUrl,
            cat.info
        )
    }
}