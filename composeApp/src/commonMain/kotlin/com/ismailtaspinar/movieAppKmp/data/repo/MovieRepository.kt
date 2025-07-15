package com.ismailtaspinar.movieAppKmp.data.repo

import com.ismailtaspinar.movieAppKmp.data.api.MovieApi
import com.ismailtaspinar.movieAppKmp.data.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.mp.KoinPlatform.getKoin

class MovieRepository(
    private val api: MovieApi = getKoin().get()
) {
    fun getTopRatedMovies(): Flow<List<Movie>> = flow {
        try {
            println("top rated movies")
            val response = api.getTopRatedMovies()
            println("response : $response")
            emit(response.results)
        } catch (e: Exception) {
            println("e : $e")
            emit(emptyList())
        }
    }

    fun getUpcomingMovies(): Flow<List<Movie>> = flow {
        try {
            val response = api.getUpcomingMovies()
            emit(response.results)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    fun getNowPlayingMovies(): Flow<List<Movie>> = flow {
        try {
            val response = api.getNowPlayingMovies()
            emit(response.results)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    fun searchMovies(query: String): Flow<List<Movie>> = flow {
        if (query.isBlank()) {
            emit(emptyList())
            return@flow
        }

        try {
            val response = api.searchMovies(query)
            emit(response.results)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    fun getMovieDetails(movieId: Int): Flow<Movie> = flow {
        try {
            val response = api.getMovieDetails(movieId)
            emit(response)
        } catch (e: Exception) {
            println("movie detail e : $e")
            emit(Movie())
        }
    }
}