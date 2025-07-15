package com.ismailtaspinar.movieAppKmp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: Int? = null,
    val title: String? = null,
    val overview: String? = null,
    val poster_path: String? = null,
    val backdrop_path: String? = null,
    val release_date: String? = null,
    val vote_average: Double? = null,
    val vote_count: Int? = null,
    val popularity: Double? = null,
    val adult: Boolean? = null,
    val original_language: String? = null,
    val original_title: String? = null,
    val genre_ids: List<Int>? = null,
    val video: Boolean? = null
) {
    val posterUrl: String
        get() = poster_path?.let { "https://image.tmdb.org/t/p/w500$it" } ?: ""

    val backdropUrl: String
        get() = backdrop_path?.let { "https://image.tmdb.org/t/p/w780$it" } ?: ""
}

@Serializable
data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)

@Serializable
data class Genre(
    val id: Int,
    val name: String
)