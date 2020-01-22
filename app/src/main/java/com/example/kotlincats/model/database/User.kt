package com.example.kotlincats.model.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_table")
class User(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val name: String,
    @ColumnInfo(name = "photoUrl") val photoUrl: String
) : Parcelable
