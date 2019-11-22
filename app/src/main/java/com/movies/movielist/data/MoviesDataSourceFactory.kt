package com.movies.movielist.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.movies.api.MoviesApi
import com.movies.model.Movie

class MoviesDataSourceFactory(private val service: MoviesApi) : DataSource.Factory<Int, Movie>() {

    val moviesLiveData = MutableLiveData<MoviesDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val source = MoviesDataSource(service)
        moviesLiveData.postValue(source)
        return source
    }

}
