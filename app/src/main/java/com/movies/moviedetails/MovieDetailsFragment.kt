package com.movies.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.movies.databinding.FragmentMovieDetailsBinding
import com.movies.di.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MovieDetailsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root

        movieDetailsViewModel.state.observe(this, Observer {
            when(it) {
//                MovieDetailsViewModel.MovieDetailsState.Loading ->
            }
        })
    }
}
