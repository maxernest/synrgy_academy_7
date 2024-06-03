package com.example.androidapp.data.remote

import com.example.androidapp.data.remote.response.MovieDetail
import com.example.androidapp.data.remote.response.NowPlayingMovie
import com.example.data.remote.request.FavoriteMovie
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Header("Authorization") token: String = "input your token here")
    : Response<MovieDetail>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Header("Authorization") token: String = "input your token here")
    : Response<NowPlayingMovie>

    @GET("account/{account_id}/favorite")
    suspend fun addFavoriteMovie(
        @Path("account_id") accountId: String,
        @Body favoriteMovie: FavoriteMovie,
        @Header("Authorization") token: String = "input your token here")
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