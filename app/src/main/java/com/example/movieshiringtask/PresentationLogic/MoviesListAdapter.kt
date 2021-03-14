package com.example.movieshiringtask.PresentationLogic

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieshiringtask.R
import com.example.movieshiringtask.businesslogic.Search
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_movie_item.view.*


class MoviesListAdapter() :
    PagedListAdapter<Search, MoviesListAdapter.MovieViewHolder>(DIFF_UTIL) {

    private val picasso = Picasso.get();

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListAdapter.MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesListAdapter.MovieViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let {
            holder.bind(it)
        }
    }

    inner class MovieViewHolder(private val view : View) : RecyclerView.ViewHolder(view) {
        fun bind(movie : Search?) {
            if (movie != null) {
                view.movieName.text = movie.title
                view.movieYear.text = movie.year
                if (!movie.poster.isNullOrEmpty()) {
                    picasso.load(movie.poster)
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(view.movieImage)
                }
            }
        }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Search>() {
            override fun areItemsTheSame(oldItem: Search, newItem: Search): Boolean {
                return oldItem.imdbID == newItem.imdbID
            }

            override fun areContentsTheSame(oldItem: Search, newItem: Search): Boolean {
                return newItem == oldItem
            }
        }
    }
}