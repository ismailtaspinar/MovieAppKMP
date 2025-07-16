package com.ismailtaspinar.movieAppKmp.ui.viewModel

import com.ismailtaspinar.movieAppKmp.data.model.Movie
import com.ismailtaspinar.movieAppKmp.data.repo.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

data class HomeUiState(
    val topRatedMovies: List<Movie> = emptyList(),
    val upcomingMovies: List<Movie> = emptyList(),
    val nowPlayingMovies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class HomeViewModel(
    private val repository: MovieRepository = MovieRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            launch {
                repository.getTopRatedMovies().collect { movies ->
                    _uiState.value = _uiState.value.copy(topRatedMovies = movies)
                }
            }

            launch {
                repository.getUpcomingMovies().collect { movies ->
                    _uiState.value = _uiState.value.copy(upcomingMovies = movies)
                }
            }

            launch {
                repository.getNowPlayingMovies().collect { movies ->
                    _uiState.value = _uiState.value.copy(
                        nowPlayingMovies = movies,
                        isLoading = false
                    )
                }
            }
        }
    }
}