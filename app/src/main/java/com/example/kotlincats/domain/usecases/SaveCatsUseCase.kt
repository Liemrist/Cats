package com.example.kotlincats.domain.usecases

import com.example.kotlincats.domain.models.Cat
import com.example.kotlincats.domain.repositories.CatRepository
import javax.inject.Inject

class SaveCatsUseCase @Inject constructor(private val catRepository: CatRepository) {

    suspend fun execute(cats: List<Cat>) {
        catRepository.saveCats(cats)
    }
}