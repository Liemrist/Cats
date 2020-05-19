package com.example.kotlincats.domain.repositories

import com.example.kotlincats.domain.models.Cat

interface CatRepository {

    suspend fun getCats(quantity: Int): List<Cat>

    suspend fun deleteCat(cat: Cat)

    suspend fun saveCats(cats: List<Cat>)
}