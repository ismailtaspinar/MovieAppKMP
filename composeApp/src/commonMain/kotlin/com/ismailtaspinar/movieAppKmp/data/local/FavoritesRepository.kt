package com.ismailtaspinar.movieAppKmp.data.local

import com.ismailtaspinar.movieAppKmp.data.model.Movie
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json


class FavoritesRepository {
    private val settings = Settings()
    private val json = Json { ignoreUnknownKeys = true }

    private val _favorites = MutableStateFlow<List<Movie>>(emptyList())
    val favorites: Flow<List<Movie>> = _favorites.asStateFlow()

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        val favoritesJson = settings.getString("favorites", "[]")
        _favorites.value = try {
            json.decodeFromString<List<Movie>>(favoritesJson)
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun addToFavorites(movie: Movie) {
        val currentFavorites = _favorites.value.toMutableList()
        if (!currentFavorites.any { it.id == movie.id }) {
            currentFavorites.add(movie)
            saveFavorites(currentFavorites)
        }
    }

    fun removeFromFavorites(movieId: Int) {
        val currentFavorites = _favorites.value.filter { it.id != movieId }
        saveFavorites(currentFavorites)
    }

    fun isFavorite(movieId: Int): Boolean {
        return _favorites.value.any { it.id == movieId }
    }

    private fun saveFavorites(favorites: List<Movie>) {
        _favorites.value = favorites
        settings["favorites"] = json.encodeToString(favorites)
    }
}