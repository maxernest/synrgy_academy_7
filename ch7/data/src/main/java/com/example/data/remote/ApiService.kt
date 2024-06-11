package com.example.androidapp.data.remote

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.androidapp.data.remote.response.MovieDetail
import com.example.androidapp.data.remote.response.MovieResponse
import com.example.data.remote.request.FavoriteMovie
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Header("Authorization") token: String = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxMjU0NzU3YmQzZTIwZTExNjVmMGRhM2QyOWJhYjE3ZiIsInN1YiI6IjY1ZGI1NzZhODI2MWVlMDE2MmMyNWQxZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.kBYBuHgZxra7yXsPYYiEUau0NfxcpWy92yjQZ_Ab6ww")
    : Response<MovieDetail>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Header("Authorization") token: String = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxMjU0NzU3YmQzZTIwZTExNjVmMGRhM2QyOWJhYjE3ZiIsInN1YiI6IjY1ZGI1NzZhODI2MWVlMDE2MmMyNWQxZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.kBYBuHgZxra7yXsPYYiEUau0NfxcpWy92yjQZ_Ab6ww")
    : Response<MovieResponse>

    @GET("account/{account_id}/favorite/movies")
    suspend fun getFavoriteMovies(
        @Path("account_id") accountId: Int,
        @Header("Authorization") token: String = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxMjU0NzU3YmQzZTIwZTExNjVmMGRhM2QyOWJhYjE3ZiIsInN1YiI6IjY1ZGI1NzZhODI2MWVlMDE2MmMyNWQxZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.kBYBuHgZxra7yXsPYYiEUau0NfxcpWy92yjQZ_Ab6ww")
            : Response<MovieResponse>

    @POST("account/{account_id}/favorite")
    suspend fun addFavoriteMovie(
        @Path("account_id") accountId: Int,
        @Body favoriteMovie: FavoriteMovie,
        @Header("Authorization") token: String = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxMjU0NzU3YmQzZTIwZTExNjVmMGRhM2QyOWJhYjE3ZiIsInN1YiI6IjY1ZGI1NzZhODI2MWVlMDE2MmMyNWQxZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.kBYBuHgZxra7yXsPYYiEUau0NfxcpWy92yjQZ_Ab6ww")
            : Response<MovieResponse>


    companion object {
        var apiService: ApiService? = null
        fun getInstance(context: Context) : ApiService {
            if (apiService == null) {
                val client = OkHttpClient.Builder()
                    .addInterceptor(ChuckerInterceptor(context))
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                apiService = retrofit.create(ApiService::class.java)
            }
            return apiService!!
        }

    }
}