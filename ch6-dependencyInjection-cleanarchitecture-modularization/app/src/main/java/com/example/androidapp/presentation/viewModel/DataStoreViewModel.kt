package com.example.androidapp.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.androidapp.data.local.DataStoreManager

class DataStoreViewModel(private val pref: DataStoreManager) : ViewModel() {

    val vUserId: MutableLiveData<Int> = MutableLiveData(0)

    suspend fun saveDataStore(value: Int) {
            pref.setUser(value)
    }

    fun getDataStore(): LiveData<Int> {
        return pref.getUser().asLiveData()
    }
}
