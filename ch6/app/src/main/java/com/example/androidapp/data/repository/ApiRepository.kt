package com.example.androidapp.data

import com.example.androidapp.api.ApiService
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getNowPlayingMovies() = apiService.getNowPlayingMovies()

    suspend fun getMovieDetails(movieId:Int) = apiService.getMovieDetails(movieId)
}