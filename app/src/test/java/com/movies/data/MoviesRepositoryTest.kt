package com.movies.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.movies.TestCoroutineRule
import com.movies.api.MoviesApi
import com.movies.movielist.data.MoviesDataSourceFactory
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MoviesRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = TestCoroutineRule()

    private var mockMoviesApi: MoviesApi = mock()
    private var mockDataSourceFactory: MoviesDataSourceFactory = mock()

    private lateinit var movies: MoviesRepository

    @Before
    fun setUp() {

        movies = MoviesRepository(mockMoviesApi)
    }

    @Test
    fun `data source factory is successfully created`() {

        val dataSourceFactory = movies.createMoviesDataSource()

        assertNotNull(dataSourceFactory)
    }

    @Test
    fun `paged list is returned when get movies is called`() {

        val pagedList = movies.getMovies(mockDataSourceFactory)

        assertNotNull(pagedList)
    }

    @Test
    fun `movies service is called when movie details are requested`() = coroutineTestRule.runBlockingTest {

        val movieId = "1234"

        movies.getMovieDetails(movieId)

        verify(mockMoviesApi).getMovieDetail(movieId)
    }
}
