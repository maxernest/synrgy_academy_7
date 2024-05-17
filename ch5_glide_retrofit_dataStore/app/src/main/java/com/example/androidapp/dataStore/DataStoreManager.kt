package com.example.androidapp.dataStore

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {

    suspend fun setUser(userId: Int) {
        context.counterDataStore.edit { preferences ->
            preferences[USER_KEY] = userId
            Log.d("dataStore", userId.toString())
        }
    }

    fun getUser(): Flow<Int> {
        return context.counterDataStore.data.map { preferences ->
            preferences[USER_KEY] ?: -1
        }
    }

    companion object {
        private const val DATASTORE_NAME = "data_store"

        private val USER_KEY = intPreferencesKey("user")

        private val Context.counterDataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }
}