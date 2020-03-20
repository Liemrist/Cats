package com.example.kotlincats.di

import com.example.kotlincats.data.api.catApi.CatApi
import com.example.kotlincats.data.api.hipsterIpsumApi.HipsterIpsumApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCatApi(retrofit: Retrofit): CatApi {
        return retrofit.create(CatApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHipsterIpsumApi(retrofit: Retrofit): HipsterIpsumApi {
        return retrofit.create(HipsterIpsumApi::class.java)
    }
}