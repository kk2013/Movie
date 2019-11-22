package com.movies.movielist

import androidx.lifecycle.MutableLiveData
import com.movies.movielist.data.MoviesDataSource
import com.movies.movielist.data.MoviesDataSourceFactory
import com.movies.movielist.data.MoviesRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MoviesTest {
    @Mock
    private lateinit var mockMoviesRepository: MoviesRepository
    @Mock
    private lateinit var mockDataSourceFactory: MoviesDataSourceFactory
    @Mock
    private lateinit var mockRepo: MutableLiveData<MoviesDataSource>

    private lateinit var MoviesViewModel: MoviesViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        whenever(mockMoviesRepository.createMoviesDataSource()).thenReturn(mockDataSourceFactory)
        whenever(mockDataSourceFactory.moviesLiveData).thenReturn(mockRepo)
    }

    @Test
    fun `init`() {
        MoviesViewModel = MoviesViewModel(mockMoviesRepository)

        verify(mockMoviesRepository).createMoviesDataSource()
        verify(mockMoviesRepository).getMovies(any())
    }
}