package com.movies.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.movies.api.MoviesService
import com.movies.model.Movie

class MovieDataSourceFactory(private val service: MoviesService) : DataSource.Factory<Int, Movie>() {

    val movieLiveData = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val source = MovieDataSource(service)
        movieLiveData.postValue(source)
        return source
    }

}
