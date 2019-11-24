package com.movies.moviedetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.movies.TestCoroutineRule
import com.movies.data.MoviesRepository
import com.movies.model.Movie
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException

class MovieDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = TestCoroutineRule()

    private var mockMoviesRepository: MoviesRepository = mock()
    private var mockHttpException: HttpException = mock()
    private var mockMovie: Movie = mock()

    private lateinit var observer: Observer<MovieDetailsViewModel.MovieDetailsState>
    private val actualValues = mutableListOf<MovieDetailsViewModel.MovieDetailsState>()

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    @Before
    fun setUp() {

        observer = Observer {
            actualValues.plusAssign(it)
        }
        movieDetailsViewModel = MovieDetailsViewModel(mockMoviesRepository)
    }

    @After
    fun tearDown() {
        actualValues.clear()
    }

    @Test
    fun `movie repo is called when movie details are requested`() = coroutineTestRule.runBlockingTest {

        val movieId = "1234"

        movieDetailsViewModel.state.observeForever(observer)

        movieDetailsViewModel.getMovieDetails(movieId)

        verify(mockMoviesRepository).getMovieDetails(movieId)
    }

    @Test
    fun `correct states are loaded when movie details are requested and an error is returned`() = coroutineTestRule.runBlockingTest {

        val movieId = "1234"

        whenever(mockMoviesRepository.getMovieDetails(any())).thenThrow(mockHttpException)

        movieDetailsViewModel.state.observeForever(observer)

        movieDetailsViewModel.getMovieDetails(movieId)

        assertEquals(2, actualValues.size)
        actualValues[0] = MovieDetailsViewModel.MovieDetailsState.Loading
        actualValues[1] = MovieDetailsViewModel.MovieDetailsState.Failed
    }

    @Test
    fun `correct states are loaded when movie details are requested and successfully returned`() = coroutineTestRule.runBlockingTest {

        val movieId = "1234"

        whenever(mockMoviesRepository.getMovieDetails(any())).thenReturn(mockMovie)

        movieDetailsViewModel.state.observeForever(observer)

        movieDetailsViewModel.getMovieDetails(movieId)

        assertEquals(2, actualValues.size)
        actualValues[0] = MovieDetailsViewModel.MovieDetailsState.Loading
        actualValues[1] = MovieDetailsViewModel.MovieDetailsState.Success(mockMovie)
    }
}
