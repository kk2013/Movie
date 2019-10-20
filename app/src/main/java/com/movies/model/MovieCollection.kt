package com.movies.model

class MovieCollection(
    val id: String,
    val name: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val parts: List<Movie>
)