package com.movies.api

import com.movies.model.MovieResponse
import com.movies.model.MovieResultsResponse
import com.movies.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesService {

    @GET("/3/movie/now_playing")
    suspend fun getMovies(): MovieResultsResponse

    @GET("/3/movie/{id}")
    suspend fun getMovieDetail(@Path("id") id: String): MovieResponse

    @GET("/3/collection/{collectionId}")
    suspend fun getMovieCollection(@Path("collectionId") collectionId: String): MoviesResponse
}
