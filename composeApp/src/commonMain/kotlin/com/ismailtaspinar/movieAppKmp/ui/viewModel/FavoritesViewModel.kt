package com.ismailtaspinar.movieAppKmp.ui.viewModel

import com.ismailtaspinar.movieAppKmp.data.local.FavoritesRepository
import com.ismailtaspinar.movieAppKmp.data.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

data class FavoritesUiState(
    val favoriteMovies: List<Movie> = emptyList()
)

class FavoritesViewModel(
    private val favoritesRepository: FavoritesRepository = FavoritesRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        observeFavorites()
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            favoritesRepository.favorites.collect { favorites ->
                _uiState.value = _uiState.value.copy(favoriteMovies = favorites)
            }
        }
    }

    fun removeFromFavorites(movieId: Int) {
        favoritesRepository.removeFromFavorites(movieId)
    }
}