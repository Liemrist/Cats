package com.example.kotlincats.domain.usecases

import com.example.kotlincats.domain.models.Cat
import com.example.kotlincats.domain.repositories.CatRepository
import javax.inject.Inject

class DeleteCatUseCase @Inject constructor(private val catRepository: CatRepository) {

    suspend fun execute(cat: Cat) {
        catRepository.deleteCat(cat)
    }
}