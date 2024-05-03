package com.example.androidapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.androidapp.entity.Note
import com.example.androidapp.entity.User

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getAll(): List<Note>

    @Query("SELECT * FROM note where username = :username")
    fun getAllByUsername(username:String): LiveData<List<Note>>

    @Query("SELECT * FROM note WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Note>

    @Query("SELECT * FROM note WHERE username = :username")
    fun findByName(username: String): LiveData<List<Note>>?

    @Query("DELETE FROM note WHERE uid = :uid")
    fun deleteByUid(uid: Int)

    @Query("UPDATE note\n" +
            "SET title = :title, note= :note\n" +
            "WHERE uid = :uid;")
    fun updateNote(uid: Int, title: String, note: String)

    @Insert
    fun insertAll(vararg users: Note)

    @Delete
    fun delete(note: Note)
}