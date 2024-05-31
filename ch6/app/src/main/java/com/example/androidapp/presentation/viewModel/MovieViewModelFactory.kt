package com.example.androidapp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidapp.data.repository.ApiRepository
import javax.inject.Inject

class MovieViewModelFactory @Inject constructor(private val repository: ApiRepository): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            MovieViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}