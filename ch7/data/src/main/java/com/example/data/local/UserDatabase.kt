package com.example.androidapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androidapp.data.local.entity.User
import com.example.androidapp.data.remote.UserDao

@Database(entities = [User::class], version = 3)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "note_database"
                )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}