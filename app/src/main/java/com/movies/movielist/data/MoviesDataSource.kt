package com.movies.movielist.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.movies.api.MoviesApi
import com.movies.data.NetworkState
import com.movies.model.Movie
import kotlinx.coroutines.runBlocking

class MoviesDataSource(
    private val service: MoviesApi
) : PageKeyedDataSource<Int, Movie>() {

    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        runBlocking {
            try {
                networkState.postValue(NetworkState.Loading)
                val movies = service.getMovies(1, params.requestedLoadSize)
                callback.onResult(movies.results, null, 2)
                networkState.postValue(NetworkState.Success)
            } catch (ex: Exception) {
                networkState.postValue(NetworkState.Failed)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        runBlocking {
            try {
                networkState.postValue(NetworkState.Loading)
                val page = params.key
                val movies = service.getMovies(1, params.requestedLoadSize)
                callback.onResult(movies.results, page + 1)
                networkState.postValue(NetworkState.Success)
            } catch (ex: Exception) {
                networkState.postValue(NetworkState.Failed)
            }
        }
    }
}
