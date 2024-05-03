package com.example.androidapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidapp.MainApplication.Companion.noteDatabase
import com.example.androidapp.NoteDatabase
import com.example.androidapp.dao.UserDao
import com.example.androidapp.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel () : ViewModel() {

    private val userDao = noteDatabase.userDao()

    fun insertUser(user: User){
        viewModelScope.launch { userDao.insertAll(user) }
    }

    fun findByEmailAndPassword(email:String, password:String): User? {
        return userDao.findByEmailAndPassword(email, password)
    }
}