package com.movies.movielist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.movies.R
import com.movies.model.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_item_row.view.*

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(movie: Movie?) {
        Picasso.get()
            .load(IMAGE_URL + movie?.poster_path)
            .placeholder(android.R.drawable.ic_menu_camera)
            .resize(IMAGE_WIDTH, IMAGE_HEIGHT)
            .centerCrop()
            .into(itemView.movieImage)
    }

    companion object {

        const val IMAGE_URL = "https://image.tmdb.org/t/p/original"
        const val IMAGE_WIDTH = 700
        const val IMAGE_HEIGHT = 1000

        fun create(parent: ViewGroup): MovieViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.movie_item_row, parent, false)
            return MovieViewHolder(view)
        }
    }
}