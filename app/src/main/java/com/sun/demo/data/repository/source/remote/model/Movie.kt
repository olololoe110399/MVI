package com.sun.demo.data.repository.source.remote.model

import com.squareup.moshi.Json

data class Movie(
    @Json(name = "id")
    val movieID: Int,
    @Json(name = "title")
    val movieTitle: String,
    @Json(name = "overview")
    val movieOverView: String,
    @Json(name = "poster_path")
    val moviePosterPath: String,
    @Json(name = "backdrop_path")
    val movieBackdropPath: String,
    @Json(name = "vote_average")
    val movieVoteAverage: Double
)
