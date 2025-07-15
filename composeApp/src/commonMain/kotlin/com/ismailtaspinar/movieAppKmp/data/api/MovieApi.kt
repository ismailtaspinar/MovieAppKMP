package com.ismailtaspinar.movieAppKmp.data.api

import com.ismailtaspinar.movieAppKmp.data.model.Movie
import com.ismailtaspinar.movieAppKmp.data.model.MovieResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess

class MovieApi(private val httpClient: HttpClient) {

    private suspend inline fun <reified T> safeApiCall(
        block: HttpClient.() -> HttpResponse
    ): T {
        val response = httpClient.block()

        return when {
            response.status.isSuccess() -> {
                response.body()
            }

            response.status == HttpStatusCode.NotFound -> {
                throw Exception("API endpoint not found: ${response.request.url}")
            }

            else -> {
                val errorBody = response.bodyAsText()
                throw Exception("API error ${response.status.value}: $errorBody")
            }
        }
    }

    suspend fun getTopRatedMovies(page: Int = 1): MovieResponse {
        return safeApiCall {
            get("3/movie/top_rated") {
                parameter("page", page)
            }
        }
    }

    suspend fun getUpcomingMovies(page: Int = 1): MovieResponse {
        return safeApiCall {
            get("3/movie/upcoming") {
                parameter("page", page)
            }
        }
    }

    suspend fun getNowPlayingMovies(page: Int = 1): MovieResponse {
        return safeApiCall {
            get("3/movie/now_playing") {
                parameter("page", page)
            }
        }
    }

    suspend fun searchMovies(query: String, page: Int = 1): MovieResponse {
        return safeApiCall {
            get("3/search/movie") {
                parameter("query", query)
                parameter("page", page)
            }
        }
    }

    suspend fun getMovieDetails(movieId: Int): Movie {
        return safeApiCall {
            get("3/movie/$movieId")
        }
    }
}