package com.movies.api

import com.movies.model.Movie
import com.movies.model.MovieCollection
import com.movies.model.Movies
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("/3/movie/now_playing")
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Movies

    @GET("/3/movie/{id}")
    suspend fun getMovieDetail(@Path("id") id: String): Movie

    @GET("/3/collection/{collectionId}")
    suspend fun getMovieCollection(@Path("collectionId") collectionId: String): MovieCollection
}
