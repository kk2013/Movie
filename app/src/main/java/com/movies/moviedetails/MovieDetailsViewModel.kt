package com.movies.moviedetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.data.MoviesRepository
import com.movies.model.Movie
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(private val movieDetailsRepository: MoviesRepository) :
    ViewModel() {

    internal val state = MutableLiveData<MovieDetailsState>()

    fun getMovieDetails(movieId: String) = viewModelScope.launch {

        state.value = MovieDetailsState.Loading
        try {
            val movieDetails = movieDetailsRepository.getMovieDetails(movieId)
            state.value = MovieDetailsState.Success(movieDetails)
        } catch (ex: Exception) {
            state.value = MovieDetailsState.Failed
        }
    }

    sealed class MovieDetailsState {
        object Loading : MovieDetailsState()
        object Failed : MovieDetailsState()
        data class Success(val movie: Movie) : MovieDetailsState()
    }
}
