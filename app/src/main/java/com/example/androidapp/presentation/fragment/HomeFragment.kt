package com.example.androidapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
import com.example.androidapp.presentation.viewModel.UserViewModel
import com.example.androidapp.presentation.viewModel.UserViewModelFactory
import javax.inject.Inject

class HomeFragment : Fragment(), onClickListener {

    private lateinit var viewModel: MovieViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataStoreViewModel: DataStoreViewModel

    @Inject
    lateinit var movieViewModelFactory: MovieViewModelFactory
    @Inject
    lateinit var userViewModelFactory: UserViewModelFactory
    @Inject
    lateinit var dataStoreViewModelFactory: DataStoreViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.initialization()

        viewModel.movieList.observe(viewLifecycleOwner) {
            recyclerView = view.findViewById<RecyclerView>(R.id.recyclerviewMovie)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = MoviesAdapter(it, this@HomeFragment)
        }

        viewModel.getNowPlayingMovies()

        view.findViewById<ImageView>(R.id.avatarImageView).setOnClickListener {
            val navigate =
                HomeFragmentDirections.actionHomeFragmentToProfileFragment2()
            findNavController().navigate(navigate)
        }

        view.findViewById<ImageView>(R.id.favoriteImageView).setOnClickListener {
            val navigate =
                HomeFragmentDirections.actionHomeFragmentToFavoriteMovieFragment2()
            findNavController().navigate(navigate)
        }

        dataStoreViewModel.getUser().observe(viewLifecycleOwner) {
            val user = userViewModel.findUserById(it)
            val name = user?.fullName ?: "no name"
            view.findViewById<TextView>(R.id.welcomeTextView).text = "Welcome, $name"
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
        userViewModel = ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)
        dataStoreViewModel = ViewModelProvider(this, dataStoreViewModelFactory).get(DataStoreViewModel::class.java)
    }
}