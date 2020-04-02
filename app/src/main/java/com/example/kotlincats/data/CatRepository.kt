package com.example.kotlincats.data

import com.example.kotlincats.data.api.ApiDataSource
import com.example.kotlincats.data.database.CatDao
import com.example.kotlincats.domain.model.Cat
import io.reactivex.Single
import javax.inject.Inject

class CatRepository @Inject constructor(
    private val catDao: CatDao,
    private val apiDataSource: ApiDataSource
) {


    fun getCats(quantity: Int): Single<List<Cat>> {
//        var cats: List<Cat> = catDao.getCats()
        // FIXME: DB is not populated here yet after storage clean.

//        if (cats.isEmpty()) {
            return apiDataSource.getCats(quantity)
//            insert(cats)
//        }

//        return cats
    }


    fun getMoreCats(quantity: Int): Single<List<Cat>> {
        return apiDataSource.getCats(quantity)
    }

//     Completable
    suspend fun insert(cats: List<Cat>) = catDao.insert(cats)


    suspend fun delete(cat: Cat) = catDao.delete(cat)
}