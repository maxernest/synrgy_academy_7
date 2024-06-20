package com.example.androidapp.presentation.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidapp.R
import com.example.androidapp.data.remote.response.Result
import com.example.androidapp.presentation.listener.onClickListener

class MoviesAdapter (private val mMovie: List<Result>, private val onClickListener: onClickListener) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_movie, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mMovie[position]

        Glide.with(holder.image.context).load("https://image.tmdb.org/t/p/original"+itemsViewModel.poster_path)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(holder.image);

        holder.title.text = itemsViewModel.title
        holder.releaseDate.text = itemsViewModel.release_date
        holder.description.text = itemsViewModel.overview

        holder.card.setOnClickListener {
            onClickListener.onItemClick(itemsViewModel.id)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        Log.d("size", mMovie.toString())
        Log.d("size", mMovie.size.toString())
        return mMovie.size

    }

    // Holds the views for adding it to image and text
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var card: CardView = itemView.findViewById(R.id.movieCardView)
        var image: ImageView = itemView.findViewById(R.id.movieImage)
        var title: TextView = itemView.findViewById(R.id.titleTextView)
        val releaseDate: TextView = itemView.findViewById(R.id.releaseDateTextView)
        val description: TextView = itemView.findViewById(R.id.descriptionTextView)
    }
}