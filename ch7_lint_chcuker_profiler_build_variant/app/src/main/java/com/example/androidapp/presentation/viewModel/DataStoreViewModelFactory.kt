package com.example.androidapp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidapp.data.local.DataStoreManager
import javax.inject.Inject

class DataStoreViewModelFactory @Inject constructor(private val pref: DataStoreManager): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DataStoreViewModel::class.java)) {
            return  DataStoreViewModel(pref) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}