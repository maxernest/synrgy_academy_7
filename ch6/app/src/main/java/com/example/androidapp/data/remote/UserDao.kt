package com.example.androidapp.data.remote

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.androidapp.data.local.entity.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE uid = :userId")
    fun findUserById(userId: Int): User?

    @Query("SELECT * FROM user WHERE email = :email AND " +
            "password = :password LIMIT 1")
    fun findByEmailAndPassword(email: String, password: String): User?

    @Query("UPDATE user\n" +
            "SET username = :username, fullName = :fullName, birthDate = :birthDate, address = :address\n" +
            "WHERE uid = :userId")
    fun updateUser(userId:Int, username:String, fullName:String, birthDate:String, address:String)

    @Insert
    fun insertAll(vararg users: User)
}