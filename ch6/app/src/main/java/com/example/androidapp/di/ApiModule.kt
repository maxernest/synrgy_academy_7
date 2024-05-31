package com.example.androidapp.di

import com.example.androidapp.data.remote.ApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideMovieAPI(): ApiService {
        return ApiService.getInstance()
    }
}