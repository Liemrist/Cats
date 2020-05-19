package com.example.kotlincats.data.database

import javax.inject.Inject

class DatabaseDataSourceImpl @Inject constructor(private val catDao: CatDao): DatabaseDataSource {

    override suspend fun getCats() {
        catDao.getCats()
    }
}