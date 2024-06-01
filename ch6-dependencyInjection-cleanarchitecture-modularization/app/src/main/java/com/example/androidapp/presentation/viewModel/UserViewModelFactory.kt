package com.example.androidapp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidapp.data.remote.UserDao
import javax.inject.Inject

class UserViewModelFactory @Inject constructor(private val userDao: UserDao): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            UserViewModel(this.userDao) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}