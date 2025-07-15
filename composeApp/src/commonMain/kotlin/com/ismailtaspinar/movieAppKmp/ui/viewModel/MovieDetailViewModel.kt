package com.ismailtaspinar.movieAppKmp.ui.viewModel

import com.ismailtaspinar.movieAppKmp.data.api.MovieApi
import com.ismailtaspinar.movieAppKmp.data.local.FavoritesRepository
import com.ismailtaspinar.movieAppKmp.data.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.koin.mp.KoinPlatform.getKoin

data class MovieDetailUiState(
    val movie: Movie? = null,
    val isLoading: Boolean = false,
    val isFavorite: Boolean = false,
    val error: String? = null
)

class MovieDetailViewModel(
    private val movieId: Int,
    private val movieApi: MovieApi = getKoin().get(),
    private val favoritesRepository: FavoritesRepository = FavoritesRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    init {
        loadMovieDetails()
        checkFavoriteStatus()
    }

    private fun loadMovieDetails() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val movie = movieApi.getMovieDetails(movieId)
                _uiState.value = _uiState.value.copy(
                    movie = movie,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }

    private fun checkFavoriteStatus() {
        _uiState.value = _uiState.value.copy(
            isFavorite = favoritesRepository.isFavorite(movieId)
        )
    }

    fun toggleFavorite() {
        val movie = _uiState.value.movie ?: return

        if (_uiState.value.isFavorite) {
            favoritesRepository.removeFromFavorites(movieId)
        } else {
            favoritesRepository.addToFavorites(movie)
        }

        checkFavoriteStatus()
    }
}