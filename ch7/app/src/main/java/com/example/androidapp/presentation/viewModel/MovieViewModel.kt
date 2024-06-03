package com.example.androidapp.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidapp.data.remote.response.MovieDetail
import com.example.androidapp.data.remote.response.Result
import com.example.androidapp.data.repository.ApiRepository
import com.example.data.remote.request.FavoriteMovie
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieViewModel constructor(private val apiRepository: ApiRepository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val movieList = MutableLiveData<List<Result>>()
    val movieDetail = MutableLiveData<MovieDetail>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getNowPlayingMovies() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = apiRepository.getNowPlayingMovies()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    movieList.postValue(response.body()?.results)
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    fun getMovieDetails(movieId:Int) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = apiRepository.getMovieDetails(movieId)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    movieDetail.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    fun addFavoriteMovie(accountId:String, favoriteMovie: FavoriteMovie) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = apiRepository.addFavoriteMovie(accountId, favoriteMovie)
            withContext(Dispatchers.Main) {
                if (!response.isSuccessful) {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}
