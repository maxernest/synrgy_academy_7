package com.example.androidapp.presentation.ui

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
import com.example.androidapp.MoviesAdapter
import com.example.androidapp.R
import com.example.androidapp.dataStore.DataStoreManager
import com.example.androidapp.onClickListener
import com.example.androidapp.viewModel.DataStoreViewModel
import com.example.androidapp.viewModel.DataStoreViewModelFactory
import com.example.androidapp.viewModel.MovieViewModel
import com.example.androidapp.viewModel.MovieViewModelFactory
import com.example.androidapp.viewModel.UserViewModel
import com.example.androidapp.viewModel.UserViewModelFactory
import javax.inject.Inject

class HomeFragment : Fragment(), onClickListener{

//    private var userViewModel = UserViewModel()
    private lateinit var viewModel: MovieViewModel
    private lateinit var userviewModel: UserViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataStoreViewModel: DataStoreViewModel
    private lateinit var pref: DataStoreManager

    @Inject
    lateinit var movieViewModelFactory: MovieViewModelFactory

    @Inject
    lateinit var userViewModelFactory: UserViewModelFactory

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

        view.findViewById<ImageView>(R.id.imageView2).setOnClickListener {
            val navigate =
                com.example.androidapp.component.HomeFragmentDirections.actionHomeFragmentToProfileFragment2()
            findNavController().navigate(navigate)
        }

        pref = DataStoreManager(requireContext())
        dataStoreViewModel = ViewModelProvider(this, DataStoreViewModelFactory(pref))[DataStoreViewModel::class.java]

        dataStoreViewModel.getDataStore().observe(viewLifecycleOwner) {
            val user = userviewModel.findUserById(it)
            val name = user?.fullName ?: "no name"
            view.findViewById<TextView>(R.id.welcomeTextView).text = "Welcome, $name"
        }
    }

    override fun onItemClick(movieId:Int) {
        val navigate =
            com.example.androidapp.component.HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(
                movieId
            )
        findNavController().navigate(navigate)
    }

    private fun initialization(){
        (getActivity()?.applicationContext as MainApplication).applicationComponent.inject(this)

        viewModel = ViewModelProvider(this, movieViewModelFactory).get(MovieViewModel::class.java)
        userviewModel = ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)
    }
}