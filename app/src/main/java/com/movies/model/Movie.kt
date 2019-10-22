package com.movies.model

data class Movie(
    val id: String,
    val title: String,
    val original_title: String,
    val original_language: String,
    val overview: String,
    val popularity: Float,
    val vote_count: Int,
    val poster_path: String,
    val release_date: String
)
