package com.movies.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.movies.data.MoviesDataSourceFactory
import com.movies.data.MoviesRepository
import com.movies.data.NetworkState
import com.movies.model.Movie
import javax.inject.Inject

class MoviesViewModel @Inject constructor(moviesRepo: MoviesRepository
) : ViewModel() {

    private val dataSourceFactory: MoviesDataSourceFactory = moviesRepo.createMoviesDataSource()
    var moviesRepo: LiveData<PagedList<Movie>>
    var networkState: LiveData<NetworkState>?

    init {
        this.moviesRepo = moviesRepo.getMovies(dataSourceFactory)
        networkState = switchMap(dataSourceFactory.moviesLiveData, { it.networkState })
    }
}