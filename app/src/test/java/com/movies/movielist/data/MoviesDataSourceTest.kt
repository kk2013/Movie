package com.movies.movielist.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PageKeyedDataSource
import com.movies.TestCoroutineRule
import com.movies.api.MoviesApi
import com.movies.data.NetworkState
import com.movies.data.Status
import com.movies.model.Movie
import com.movies.model.Movies
import com.nhaarman.mockito_kotlin.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException

@ExperimentalCoroutinesApi
class MoviesDataSourceTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = TestCoroutineRule()

    private var mockInitialCallback: PageKeyedDataSource.LoadInitialCallback<Int, Movie> = mock()
    private var mockCallback: PageKeyedDataSource.LoadCallback<Int, Movie> = mock()
    private var mockService: MoviesApi = mock()
    private var mockHttpException: HttpException = mock()

    private val mockInitialParams: PageKeyedDataSource.LoadInitialParams<Int> =
        PageKeyedDataSource.LoadInitialParams(2, true)
    private val mockParams: PageKeyedDataSource.LoadParams<Int> =
        PageKeyedDataSource.LoadParams(2, 12)

    private lateinit var observer: Observer<NetworkState>

    private val actualValues = mutableListOf<NetworkState>()

    private lateinit var moviesDataSource: MoviesDataSource

    @Before
    fun setUp() {
        observer = Observer {
            actualValues.plusAssign(it)
        }

        moviesDataSource = MoviesDataSource(mockService)
    }

    @After
    fun tearDown() {
        actualValues.clear()
    }

    @Test
    fun `initial paging load failed`() = coroutineTestRule.runBlockingTest {

        whenever(mockService.getMovies(any(), any())).thenThrow(mockHttpException)

        moviesDataSource.networkState.observeForever(observer)

        moviesDataSource.loadInitial(mockInitialParams, mockInitialCallback)

        verify(mockInitialCallback, never()).onResult(emptyList(), null, 2)
        assertEquals(2, actualValues.size)
        assertEquals(Status.LOADING, actualValues[0].status)
        assertEquals(Status.FAILED, actualValues[1].status)
    }

    @Test
    fun `initial paging load successful`() = coroutineTestRule.runBlockingTest {

        whenever(mockService.getMovies(any(), any())).thenReturn(Movies(emptyList()))

        moviesDataSource.networkState.observeForever(observer)

        moviesDataSource.loadInitial(mockInitialParams, mockInitialCallback)

        verify(mockInitialCallback).onResult(emptyList(), null, 2)
        assertEquals(2, actualValues.size)
        assertEquals(Status.LOADING, actualValues[0].status)
        assertEquals(Status.SUCCESS, actualValues[1].status)
    }

    @Test
    fun `load after paging load failed`() = coroutineTestRule.runBlockingTest {

        whenever(mockService.getMovies(any(), any())).thenThrow(mockHttpException)

        moviesDataSource.networkState.observeForever(observer)

        moviesDataSource.loadAfter(mockParams, mockCallback)

        verify(mockCallback, never()).onResult(emptyList(), 3)
        assertEquals(2, actualValues.size)
        assertEquals(Status.LOADING, actualValues[0].status)
        assertEquals(Status.FAILED, actualValues[1].status)
    }

    @Test
    fun `load after paging load successful`() = coroutineTestRule.runBlockingTest {

        whenever(mockService.getMovies(any(), any())).thenReturn(Movies(emptyList()))

        moviesDataSource.networkState.observeForever(observer)

        moviesDataSource.loadAfter(mockParams, mockCallback)

        verify(mockCallback).onResult(emptyList(), 3)
        assertEquals(2, actualValues.size)
        assertEquals(Status.LOADING, actualValues[0].status)
        assertEquals(Status.SUCCESS, actualValues[1].status)
    }
}