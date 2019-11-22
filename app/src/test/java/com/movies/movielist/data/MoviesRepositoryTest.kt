package com.movies.movielist.data

import com.movies.api.MoviesService
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MoviesRepositoryTest {

    @Mock
    private lateinit var mockService: MoviesService
    @Mock
    private lateinit var mockDataSourceFactory: MoviesDataSourceFactory

    private lateinit var movies: MoviesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        movies = MoviesRepository(mockService)
    }

    @Test
    fun `create data source factory`() {

        val dataSourceFactory = movies.createMoviesDataSource()

        assertNotNull(dataSourceFactory)
    }

    @Test
    fun `get Movies`() {

        val pagedList = movies.getMovies(mockDataSourceFactory)

        assertNotNull(pagedList)
    }
}