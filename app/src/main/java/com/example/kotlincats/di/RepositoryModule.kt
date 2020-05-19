package com.example.kotlincats.di

import com.example.kotlincats.data.CatRepositoryImpl
import com.example.kotlincats.data.api.ApiDataSource
import com.example.kotlincats.data.api.ApiDataSourceImpl
import com.example.kotlincats.domain.repositories.CatRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindCatRepository(catRepository: CatRepositoryImpl): CatRepository

    @Binds
    abstract fun bindApiDataSource(apiDataSourceImpl: ApiDataSourceImpl): ApiDataSource
}