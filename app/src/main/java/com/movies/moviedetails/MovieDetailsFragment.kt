package com.movies.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.movies.databinding.FragmentMovieDetailsBinding
import com.movies.di.ViewModelFactory
import com.movies.model.Movie
import com.movies.movielist.MovieViewHolder
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.movie_item_row.view.*

class MovieDetailsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels {
        viewModelFactory
    }

    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieDetailsViewModel.state.observe(this, Observer {
            when (it) {
                is MovieDetailsViewModel.MovieDetailsState.Success -> updateMovieDetail(it.movie)
                MovieDetailsViewModel.MovieDetailsState.Loading -> progress_bar.visibility = View.VISIBLE
                MovieDetailsViewModel.MovieDetailsState.Failed -> showError()
            }
        })
        movieDetailsViewModel.getMovieDetails(args.movieId)
    }

    private fun showError() {
        progress_bar.visibility = View.GONE
    }

    private fun updateMovieDetail(movie: Movie) {
        progress_bar.visibility = View.GONE
        title.text = movie.title
        overview.text = movie.overview
        Picasso.get()
            .load(MovieViewHolder.IMAGE_URL + movie.poster_path)
            .resize(MovieViewHolder.IMAGE_WIDTH, MovieViewHolder.IMAGE_HEIGHT)
            .centerCrop()
            .into(movie_image)
    }
}
