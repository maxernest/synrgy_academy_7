package com.example.androidapp.di

import android.content.Context
import com.example.androidapp.data.local.DataStoreManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataStoreModule {

    @Singleton
    @Provides
    fun provideDatastore(context: Context): DataStoreManager {
        return DataStoreManager(context)
    }
}