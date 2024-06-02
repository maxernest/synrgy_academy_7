package com.example.androidapp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidapp.data.remote.UserDao
import com.example.androidapp.data.local.entity.User
import kotlinx.coroutines.launch

class UserViewModel (val userDao: UserDao) : ViewModel() {

//    private val userDao = userDatabase.userDao()

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