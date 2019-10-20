package com.movies.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.movies.api.MoviesService
import com.movies.model.Movie
import java.util.concurrent.Executors
import javax.inject.Inject

class MovieRepository @Inject constructor(private val service: MoviesService) {

    fun getMovies(dataSourceFactory: MovieDataSourceFactory): LiveData<PagedList<Movie>> {
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

    fun createMoviesDataSource(): MovieDataSourceFactory {
        return MovieDataSourceFactory(service)
    }

    companion object {
        const val PAGE_SIZE = 12
        const val PREFETCH_SIZE = 6
        const val INITIAL_LOAD_SIZE = 20
    }
}
