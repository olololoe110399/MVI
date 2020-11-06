package com.sun.demo.data.model

import com.squareup.moshi.Json

data class Movie(
    @field:Json(name = "id")
    val movieID: Int,
    @field:Json(name = "title")
    val movieTitle: String,
    @field:Json(name = "overview")
    val movieOverView: String,
    @field:Json(name = "poster_path")
    val moviePosterPath: String,
    @field:Json(name = "backdrop_path")
    val movieBackdropPath: String,
    @field:Json(name = "vote_average")
    val movieVoteAverage: Double
)
