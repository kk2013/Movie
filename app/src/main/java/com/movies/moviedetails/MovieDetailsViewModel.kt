package com.movies.moviedetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuck.util.wrapEspressoIdlingResource
import com.movies.data.MoviesRepository
import com.movies.model.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(private val movieDetailsRepository: MoviesRepository) : ViewModel() {

    private val _state = MutableLiveData<MovieDetailsState>()
    val state: MutableLiveData<MovieDetailsState>
        get() = _state

    fun getMovieDetails(movieId: String) = viewModelScope.launch {

        _state.value = MovieDetailsState.Loading
        wrapEspressoIdlingResource {
            try {
                val movieDetails = movieDetailsRepository.getMovieDetails(movieId)
                _state.value = MovieDetailsState.Success(movieDetails)
            } catch (ex: Exception) {
                _state.value = MovieDetailsState.Failed
            }
        }
    }

    sealed class MovieDetailsState {
        object Loading : MovieDetailsState()
        object Failed : MovieDetailsState()
        data class Success(val movie: Movie) : MovieDetailsState()
    }
}
