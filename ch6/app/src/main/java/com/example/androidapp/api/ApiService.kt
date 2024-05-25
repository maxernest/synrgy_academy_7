package com.example.androidapp.api

import com.example.androidapp.api.response.MovieDetail
import com.example.androidapp.api.response.NowPlayingMovie
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Header("Authorization") token: String = "silahkan masukan token anda")
    : Response<MovieDetail>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Header("Authorization") token: String = "silahkan masukan token anda")
            : Response<NowPlayingMovie>

    companion object {
        var apiService: ApiService? = null
        fun getInstance() : ApiService {
            if (apiService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                apiService = retrofit.create(ApiService::class.java)
            }
            return apiService!!
        }

    }
}