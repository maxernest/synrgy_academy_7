package com.example.data

import com.example.androidapp.data.remote.ApiService
import com.example.androidapp.data.remote.response.MovieDetail
import com.example.androidapp.data.remote.response.MovieResponse
import com.example.androidapp.data.repository.ApiRepository
import com.example.data.remote.request.FavoriteMovie
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ApiRepositoryUnitTest {

    private lateinit var repo: ApiRepository
    private lateinit var service: ApiService

    @Before
    fun setUp(){
        service = mockk()
        repo = ApiRepository(service)
    }

    @Test
    fun getNowPlayingMovies():Unit = runBlocking {
        val movieResponse = mockk<Response<MovieResponse>>()
        every{
            runBlocking {
                service.getNowPlayingMovies()
            }
        }returns movieResponse

        repo.getNowPlayingMovies()

        verify {
            runBlocking { service.getNowPlayingMovies() }
        }
    }

    @Test
    fun getMovieDetails():Unit = runBlocking {
        val movieResponse = mockk<Response<MovieDetail>>()
        every{
            runBlocking {
                service.getMovieDetails(1)
            }
        }returns movieResponse

        repo.getMovieDetails(1)

        verify {
            runBlocking { service.getMovieDetails(1) }
        }
    }

    @Test
    fun getFavoriteMovies():Unit = runBlocking {
        val movieResponse = mockk<Response<MovieResponse>>()
        every{
            runBlocking {
                service.getFavoriteMovies(1)
            }
        }returns movieResponse

        repo.getFavoriteMovies(1)

        verify {
            runBlocking { service.getFavoriteMovies(1) }
        }
    }

    @Test
    fun addFavoriteMovie():Unit = runBlocking {
        val movieResponse = mockk<Response<MovieResponse>>()
        every{
            runBlocking {
                service.addFavoriteMovie(1, FavoriteMovie(1))
            }
        }returns movieResponse

        repo.addFavoriteMovie(1, FavoriteMovie(1))

        verify {
            runBlocking { service.addFavoriteMovie(1, FavoriteMovie(1)) }
        }
    }
}