package com.movies.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.movies.api.MoviesApi
import com.movies.model.Movie
import com.movies.movielist.data.MoviesDataSourceFactory
import java.util.concurrent.Executors
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val moviesApi: MoviesApi) {

    fun getMovies(dataSourceFactory: MoviesDataSourceFactory): LiveData<PagedList<Movie>> {
        val pagedList = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(INITIAL_LOAD_SIZE)
            .setPageSize(PAGE_SIZE)
            .setPrefetchDistance(PREFETCH_SIZE)
            .build()

        return LivePagedListBuilder(dataSourceFactory, pagedList)
            .setInitialLoadKey(1)
            .setFetchExecutor(Executors.newFixedThreadPool(3))
            .build()
    }

    fun createMoviesDataSource(): MoviesDataSourceFactory {
        return MoviesDataSourceFactory(moviesApi)
    }

    suspend fun getMovieDetails(movieId: String): Movie = moviesApi.getMovieDetail(movieId)

    companion object {
        const val PAGE_SIZE = 30
        const val PREFETCH_SIZE = 6
        const val INITIAL_LOAD_SIZE = 40
    }
}
