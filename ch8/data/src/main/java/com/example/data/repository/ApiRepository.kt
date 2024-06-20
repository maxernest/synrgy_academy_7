package com.example.androidapp.data.repository

import com.example.androidapp.data.remote.ApiService
import com.example.data.remote.request.FavoriteMovie
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getNowPlayingMovies() = apiService.getNowPlayingMovies()

    suspend fun getMovieDetails(movieId:Int) = apiService.getMovieDetails(movieId)

    suspend fun getFavoriteMovies(accountId:Int) = apiService.getFavoriteMovies(accountId)

    suspend fun addFavoriteMovie(accountId:Int, favoriteMovie:FavoriteMovie) = apiService.addFavoriteMovie(accountId, favoriteMovie)
}