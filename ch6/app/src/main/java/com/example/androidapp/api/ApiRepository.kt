package com.example.androidapp.api

class ApiRepository constructor(private val apiService: ApiService) {

    suspend fun getNowPlayingMovies() = apiService.getNowPlayingMovies()

    suspend fun getMovieDetails(movieId:Int) = apiService.getMovieDetails(movieId)
}