package com.example.kotlincats.utils

import com.example.kotlincats.api.catApi.CatDto
import com.example.kotlincats.model.User

class Mappers {

    companion object {

        fun mapCatsWithTextToUsers(
            cats: List<CatDto>,
            hipsterText: List<String>
        ): List<User> {
            val users: ArrayList<User> = arrayListOf()
            for ((index, cat) in cats.withIndex()) {
                val textFragments = hipsterText[index].split(" ", limit = 2)

                users.add(
                    User(
                        cat.hashCode(),
                        textFragments[0],
                        cat.imageUrl,
                        textFragments[1]
                    )
                )
            }

            return users
        }
    }
}