package com.example.kotlincats.data.database

interface DatabaseDataSource {

    suspend fun getCats()
}