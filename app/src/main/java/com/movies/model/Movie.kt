package com.movies.model

data class Movie(
    val id: String,
    val originalTitle: String,
    val originalLanguage: String,
    val overview: String,
    val popularity: String,
    val posterPath: String,
    val collection: Collection? = null
)

data class Collection(
    val collectionId: String
)
