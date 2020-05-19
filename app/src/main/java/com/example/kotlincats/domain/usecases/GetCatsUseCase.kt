package com.example.kotlincats.domain.usecases

import com.example.kotlincats.domain.models.Cat
import com.example.kotlincats.domain.repositories.CatRepository
import javax.inject.Inject

class GetCatsUseCase @Inject constructor(private val catRepository: CatRepository) {

    suspend fun execute(quantity: Int): List<Cat> {
        return catRepository.getCats(quantity)
    }
}