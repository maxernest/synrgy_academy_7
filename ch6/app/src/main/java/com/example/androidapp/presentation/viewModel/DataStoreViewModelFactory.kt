package com.example.androidapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidapp.dataStore.DataStoreManager

class DataStoreViewModelFactory constructor(private val pref: DataStoreManager): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DataStoreViewModel::class.java)) {
            return  DataStoreViewModel(pref) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}