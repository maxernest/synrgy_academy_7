package com.example.androidapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidapp.MainApplication.Companion.userDatabase
import com.example.androidapp.entity.User
import kotlinx.coroutines.launch

class UserViewModel () : ViewModel() {

    private val userDao = userDatabase.userDao()

    fun insertUser(user: User){
        viewModelScope.launch { userDao.insertAll(user) }
    }

    fun findByEmailAndPassword(email:String, password:String): User? {
        return userDao.findByEmailAndPassword(email, password)
    }

    fun findUserById(userId: Int): User? {
        return userDao.findUserById(userId)
    }

    fun updateUser(userId:Int, username:String, fullName:String, birthDate:String, address:String){
        viewModelScope.launch { userDao.updateUser(userId, username, fullName, birthDate, address) }
    }

}