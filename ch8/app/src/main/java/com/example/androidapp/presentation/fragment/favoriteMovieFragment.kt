package com.example.androidapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapp.MainApplication
import com.example.androidapp.R
import com.example.androidapp.presentation.listener.onClickListener
import com.example.androidapp.presentation.ui.adapter.MoviesAdapter
import com.example.androidapp.presentation.viewModel.DataStoreViewModel
import com.example.androidapp.presentation.viewModel.DataStoreViewModelFactory
import com.example.androidapp.presentation.viewModel.MovieViewModel
import com.example.androidapp.presentation.viewModel.MovieViewModelFactory
import javax.inject.Inject

class FavoriteMovieFragment : Fragment(), onClickListener {

    private lateinit var viewModel: MovieViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataStoreViewModel: DataStoreViewModel

    @Inject
    lateinit var movieViewModelFactory: MovieViewModelFactory
    @Inject
    lateinit var dataStoreViewModelFactory: DataStoreViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.initialization()

        viewModel.movieList.observe(viewLifecycleOwner) {
            recyclerView = view.findViewById<RecyclerView>(R.id.recyclerviewMovie)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = MoviesAdapter(it, this@FavoriteMovieFragment)
        }

        dataStoreViewModel.getAccount().observe(viewLifecycleOwner) {
            viewModel.getFavoriteMovies(it)
        }
    }

    override fun onItemClick(movieId:Int) {
        val navigate =
            HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(
                movieId
            )
        findNavController().navigate(navigate)
    }

    private fun initialization(){
        (getActivity()?.applicationContext as MainApplication).applicationComponent.inject(this)

        viewModel = ViewModelProvider(this, movieViewModelFactory).get(MovieViewModel::class.java)
        dataStoreViewModel = ViewModelProvider(this, dataStoreViewModelFactory).get(DataStoreViewModel::class.java)
    }
}