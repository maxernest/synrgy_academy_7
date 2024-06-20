package com.example.androidapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "username") val username: String?,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "password") val password: String?,
    @ColumnInfo(name = "fullName") val fullName: String? = null,
    @ColumnInfo(name = "birthDate") val birthDate: String? = null,
    @ColumnInfo(name = "address") val address: String? = null,
    @ColumnInfo(name = "accountId") val accountId: Int = 21030803,
)
