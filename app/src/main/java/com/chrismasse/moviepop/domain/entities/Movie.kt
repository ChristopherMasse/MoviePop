package com.chrismasse.moviepop.domain.entities

data class Movie(
    val id: Int,
    val title: String,
    val tagline: String,
    val overview: String,
    val rating: Double,
    val genre: String,
    val releaseDate: String,
    val posterPath: String,
    val backdropPath: String,
    val logos: List<Image>,
    val backdrops: List<Image>,
    val posters: List<Image>,
    val actors: List<Actor>
)
