package com.movies.movielist

import androidx.lifecycle.MutableLiveData
import com.movies.data.MoviesRepository
import com.movies.movielist.data.MoviesDataSource
import com.movies.movielist.data.MoviesDataSourceFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test

class MoviesViewModelTest {
    private var mockMoviesRepository: MoviesRepository = mock()
    private var mockDataSourceFactory: MoviesDataSourceFactory = mock()
    private var mockRepo: MutableLiveData<MoviesDataSource> = mock()

    private lateinit var moviesViewModel: MoviesViewModel

    @Before
    fun setUp() {

        whenever(mockMoviesRepository.createMoviesDataSource()).thenReturn(mockDataSourceFactory)
        whenever(mockDataSourceFactory.moviesLiveData).thenReturn(mockRepo)

        moviesViewModel = MoviesViewModel(mockMoviesRepository)
    }

    @Test
    fun `init`() {

        verify(mockMoviesRepository).createMoviesDataSource()
        verify(mockMoviesRepository).getMovies(any())
    }
}
