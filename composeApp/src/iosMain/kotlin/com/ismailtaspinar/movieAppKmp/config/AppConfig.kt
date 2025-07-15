package com.ismailtaspinar.movieAppKmp.config

actual object AppConfig {
    actual val API_KEY: String = getApiKeyFromBundle().also { println(it) }
    actual val BASE_URL: String = "https://api.themoviedb.org/3"
}

private fun getApiKeyFromBundle(): String {
    return platform.Foundation.NSBundle.mainBundle.objectForInfoDictionaryKey("TMDB_API_KEY") as? String
        ?: "default_api_key"
}