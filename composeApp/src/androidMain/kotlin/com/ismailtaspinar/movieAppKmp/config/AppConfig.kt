package com.ismailtaspinar.movieAppKmp.config

actual object AppConfig {
    actual val API_KEY: String =  com.ismailtaspinar.movieAppKmp.BuildConfig.TMDB_API_KEY
    actual val BASE_URL: String = "https://api.themoviedb.org/3"
}