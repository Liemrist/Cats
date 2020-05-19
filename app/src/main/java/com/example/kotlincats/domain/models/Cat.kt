package com.example.kotlincats.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Cat(
    val id: Int,
    val name: String,
    val photoUrl: String,
    val info: String
) : Parcelable
