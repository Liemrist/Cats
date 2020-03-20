package com.example.kotlincats.data

import com.example.kotlincats.data.api.catApi.CatApi
import com.example.kotlincats.data.api.hipsterIpsumApi.HipsterIpsumApi
import com.example.kotlincats.data.database.CatDao
import com.example.kotlincats.domain.model.Cat
import javax.inject.Inject

class CatRepository @Inject constructor(
    private val catDao: CatDao,
    private val catApi: CatApi,
    private val hipsterIpsumApi: HipsterIpsumApi,
    private val catMapper: CatMapper
) {

    suspend fun getCats(): List<Cat> {
        var cats: List<Cat> = catDao.getUsers()
        // FIXME: DB is not populated here yet after storage clean.

        if (cats.isEmpty()) {
            val catsFromApi = catApi.getCats(30)
            val text = hipsterIpsumApi.getParagraphs(30);

            cats = catMapper.mapCatsWithTextToUsers(catsFromApi, text)
            insert(cats)
        }

        return cats
    }


    suspend fun insert(cat: Cat) = catDao.insert(cat)


    suspend fun insert(cats: List<Cat>) = catDao.insert(cats)


    suspend fun delete(cat: Cat) = catDao.delete(cat)
}