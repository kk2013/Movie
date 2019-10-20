package com.movies.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.chuck.util.wrapEspressoIdlingResource
import com.movies.api.MoviesService
import com.movies.model.Movie
import kotlinx.coroutines.runBlocking

class MovieDataSource(private val service: MoviesService) : PageKeyedDataSource<Int, Movie>() {

    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        wrapEspressoIdlingResource {
            runBlocking {
                try {
                    networkState.postValue(NetworkState.LOADING)
                    val movies = service.getMovies(1, params.requestedLoadSize)
                    callback.onResult(movies.results, null, 2)
                    networkState.postValue(NetworkState.SUCCESS)
                } catch (ex: Exception) {
                    networkState.postValue(NetworkState.FAILED)
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        wrapEspressoIdlingResource {
            runBlocking {
                try {
                    networkState.postValue(NetworkState.LOADING)
                    val page = params.key
                    val movies = service.getMovies(1, params.requestedLoadSize)
                    callback.onResult(movies.results, page + 1)
                    networkState.postValue(NetworkState.SUCCESS)
                } catch (ex: Exception) {
                    networkState.postValue(NetworkState.FAILED)
                }
            }
        }
    }
}
