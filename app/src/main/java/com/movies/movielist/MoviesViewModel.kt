package com.movies.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.movies.data.MovieDataSourceFactory
import com.movies.data.MovieRepository
import com.movies.data.NetworkState
import com.movies.model.Movie
import javax.inject.Inject

class MoviesViewModel @Inject constructor(movieRepo: MovieRepository) : ViewModel() {

    private val dataSourceFactory: MovieDataSourceFactory = movieRepo.createMoviesDataSource()
    var moviesRepo: LiveData<PagedList<Movie>>
    var networkState: LiveData<NetworkState>?

    init {
        moviesRepo = movieRepo.getMovies(dataSourceFactory)
        networkState = switchMap(dataSourceFactory.movieLiveData, { it.networkState })
    }
}