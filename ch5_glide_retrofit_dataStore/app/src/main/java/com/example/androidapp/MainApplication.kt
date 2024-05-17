package com.example.androidapp

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.androidapp.api.ApiService

class MainApplication: Application() {
    val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "dataStore")

    companion object{
        lateinit var userDatabase: UserDatabase
        lateinit var apiService: ApiService
    }

    override fun onCreate() {
        super.onCreate()

        userDatabase = UserDatabase.getDatabase(applicationContext)
        apiService = ApiService.getInstance()
    }
}