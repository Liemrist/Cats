package com.example.kotlincats.data

import com.example.kotlincats.data.api.ApiDataSource
import com.example.kotlincats.data.database.CatDao
import com.example.kotlincats.data.mappers.CatMapper
import com.example.kotlincats.domain.models.Cat
import com.example.kotlincats.domain.repositories.CatRepository
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(
    private val catDao: CatDao,
    private val apiDataSource: ApiDataSource,
    private val catMapper: CatMapper
): CatRepository {

    override suspend fun getCats(quantity: Int): List<Cat> {
        return apiDataSource.getCats(quantity).map { catMapper.toCat(it) }
    }

    override suspend fun deleteCat(cat: Cat) = catDao.delete(
        catMapper.toCatDatabaseModel(cat)
    )

    override suspend fun saveCats(cats: List<Cat>) = catDao.insert(
        cats.map { catMapper.toCatDatabaseModel(it) }
    )
}