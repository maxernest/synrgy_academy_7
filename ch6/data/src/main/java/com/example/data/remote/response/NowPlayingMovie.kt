package com.example.androidapp.data.remote.response

data class NowPlayingMovie(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)