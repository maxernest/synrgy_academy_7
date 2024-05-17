package com.example.androidapp.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.androidapp.R
import com.example.androidapp.api.ApiRepository
import com.example.androidapp.api.ApiService
import com.example.androidapp.api.response.MovieDetail
import com.example.androidapp.viewModel.MovieViewModel
import com.example.androidapp.viewModel.MovieViewModelFactory

class MovieDetailFragment : Fragment() {

    private lateinit var viewModel: MovieViewModel
    private val safeArgs: MovieDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiService = ApiService.getInstance()
        val apiRepository = ApiRepository(apiService)

        viewModel = ViewModelProvider(this, MovieViewModelFactory(apiRepository)).get(MovieViewModel::class.java)


        viewModel.movieDetail.observe(viewLifecycleOwner) {
            insertDataToView(it)
        }

        viewModel.getMovieDetails(safeArgs.movieId)
    }

    private fun insertDataToView(movieDetail: MovieDetail){
        view?.findViewById<TextView>(R.id.titleTextView)?.text = movieDetail.title
        view?.findViewById<ImageView>(R.id.movieImage)?.let {
            Glide.with(requireContext()).load("https://image.tmdb.org/t/p/original"+movieDetail.poster_path)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(it)
        };
        view?.findViewById<TextView>(R.id.releaseDateTextView)?.text = movieDetail.release_date
        view?.findViewById<TextView>(R.id.runtimeTextView)?.text = movieDetail.runtime.toString() + " Minutes"
        view?.findViewById<TextView>(R.id.languangeTextView)?.text = movieDetail.spoken_languages[0].english_name
        view?.findViewById<TextView>(R.id.genreTextView)?.text = movieDetail.genres.joinToString { genre -> genre.name }
        view?.findViewById<TextView>(R.id.descriptionTextView)?.text = movieDetail.overview
    }
}