package com.example.androidapp

import android.content.Context
import com.example.androidapp.data.remote.response.MovieDetail
import com.example.androidapp.data.remote.response.MovieResponse
import com.example.androidapp.data.repository.ApiRepository
import com.example.androidapp.presentation.viewModel.MovieViewModel
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
class MovieViewModelUnitTest {

    private lateinit var viewModel: MovieViewModel
    private lateinit var repository: ApiRepository

    @Before
    fun setUp(){
        repository = mockk()
        viewModel = MovieViewModel(repository)
    }

    @Test
    fun getNowPlayingMovies():Unit = runBlocking {
        val movieResponse = mockk<Response<MovieResponse>>()
        every{
            runBlocking {
                repository.getNowPlayingMovies()
            }
        } returns movieResponse

        viewModel.getNowPlayingMovies()

        verify {
            runBlocking { repository.getNowPlayingMovies() }
        }
    }

    @Test
    fun getMovieDetails():Unit = runBlocking {
        val movieResponse = mockk<Response<MovieDetail>>()
        every{
            runBlocking {
                repository.getMovieDetails(1)
            }
        } returns movieResponse

        viewModel.getMovieDetails(1)

        verify {
            runBlocking { repository.getMovieDetails(1) }
        }
    }

    @Test
    fun getFavoriteMovies():Unit = runBlocking {
        val movieResponse = mockk<Response<MovieResponse>>()
        every{
            runBlocking {
                repository.getFavoriteMovies(1)
            }
        } returns movieResponse

        viewModel.getFavoriteMovies(1)

        verify {
            runBlocking { repository.getFavoriteMovies(1) }
        }
    }

    @Test
    fun addFavoriteMovie():Unit = runBlocking {
        val mContextMock = mockk<Context>(relaxed = true)
        val movieResponse = mockk<Response<MovieResponse>>()
        every{
            runBlocking {
                repository.addFavoriteMovie(1, FavoriteMovie(1))
            }
        } returns movieResponse

        viewModel.addFavoriteMovie(1, FavoriteMovie(1),mContextMock)

        verify {
            runBlocking { repository.addFavoriteMovie(1, FavoriteMovie(1)) }
        }
    }
}