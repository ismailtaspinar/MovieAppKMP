package com.ismailtaspinar.movieAppKmp.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ismailtaspinar.movieAppKmp.data.model.Movie
import com.ismailtaspinar.movieAppKmp.navigation.LocalNavigator
import com.ismailtaspinar.movieAppKmp.navigation.Screen
import com.ismailtaspinar.movieAppKmp.ui.components.MovieCard
import com.ismailtaspinar.movieAppKmp.ui.theme.AppColors
import com.ismailtaspinar.movieAppKmp.ui.viewModel.HomeUiState
import com.ismailtaspinar.movieAppKmp.ui.viewModel.HomeViewModel
import moe.tlaster.precompose.viewmodel.viewModel
import movieappkmp.composeapp.generated.resources.Res
import movieappkmp.composeapp.generated.resources.home_subtitle
import movieappkmp.composeapp.generated.resources.home_title
import movieappkmp.composeapp.generated.resources.loading_movies
import movieappkmp.composeapp.generated.resources.movies_count
import movieappkmp.composeapp.generated.resources.no_movies
import movieappkmp.composeapp.generated.resources.now_playing_title
import movieappkmp.composeapp.generated.resources.top_rated_title
import movieappkmp.composeapp.generated.resources.upcoming_title
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen() {
    val navigator = LocalNavigator.current
    val viewModel = viewModel(HomeViewModel::class) { HomeViewModel() }
    val uiState by viewModel.uiState.collectAsState()

    HomeScreenContent(
        uiState = uiState,
        navigateToMovieDetail = { movieId ->
            navigator.navigate(Screen.MovieDetail(movieId).route)
        }
    )
}

@Composable
internal fun HomeScreenContent(
    uiState: HomeUiState,
    navigateToMovieDetail: (Int) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        AppColors.gradientStart,
                        AppColors.gradientMiddle,
                        AppColors.gradientEnd
                    )
                )
            )
    ) {
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CircularProgressIndicator(
                        color = AppColors.primary,
                        strokeWidth = 4.dp,
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = stringResource(Res.string.loading_movies),
                        style = MaterialTheme.typography.bodyLarge,
                        color = AppColors.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Column(
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
                    ) {
                        Text(
                            text = stringResource(Res.string.home_title),
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 28.sp
                            ),
                            color = AppColors.onSurface
                        )
                        Text(
                            text = stringResource(Res.string.home_subtitle),
                            style = MaterialTheme.typography.bodyLarge,
                            color = AppColors.onSurfaceVariant
                        )
                    }
                }

                item {
                    MovieSection(
                        title = stringResource(Res.string.top_rated_title),
                        subtitle = stringResource(
                            Res.string.movies_count,
                            uiState.topRatedMovies.size
                        ),
                        movies = uiState.topRatedMovies,
                        gradientColor = AppColors.primary,
                        onMovieClick = { movie ->
                            navigateToMovieDetail(movie.id ?: 0)
                        }
                    )
                }

                item {
                    MovieSection(
                        title = stringResource(Res.string.upcoming_title),
                        subtitle = stringResource(
                            Res.string.movies_count,
                            uiState.upcomingMovies.size
                        ),
                        movies = uiState.upcomingMovies,
                        gradientColor = AppColors.secondary,
                        onMovieClick = { movie ->
                            navigateToMovieDetail(movie.id ?: 0)
                        }
                    )
                }

                item {
                    MovieSection(
                        title = stringResource(Res.string.now_playing_title),
                        subtitle = stringResource(
                            Res.string.movies_count,
                            uiState.nowPlayingMovies.size
                        ),
                        movies = uiState.nowPlayingMovies,
                        gradientColor = AppColors.accent,
                        onMovieClick = { movie ->
                            navigateToMovieDetail(movie.id ?: 0)
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}


@Preview
@Composable
fun MovieSection(
    title: String = "Title",
    subtitle: String = "Subtitle",
    movies: List<Movie> = emptyList(),
    gradientColor: androidx.compose.ui.graphics.Color = AppColors.primary,
    onMovieClick: (Movie) -> Unit = {}
) {
    var isVisible by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 600),
        label = "sectionAlpha"
    )

    androidx.compose.runtime.LaunchedEffect(movies) {
        if (movies.isNotEmpty()) {
            isVisible = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(alpha)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            color = AppColors.surface.copy(alpha = 0.7f),
            shape = RoundedCornerShape(16.dp),
            shadowElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                gradientColor.copy(alpha = 0.1f),
                                gradientColor.copy(alpha = 0.05f)
                            )
                        )
                    )
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                        color = AppColors.onSurface
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppColors.onSurfaceVariant
                    )
                }

                Surface(
                    modifier = Modifier.size(12.dp),
                    color = gradientColor,
                    shape = RoundedCornerShape(50)
                ) {}
            }
        }

        if (movies.isNotEmpty()) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                items(movies) { movie ->
                    MovieCard(
                        movie = movie,
                        onClick = { onMovieClick(movie) }
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    color = AppColors.surfaceVariant.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "📭",
                            fontSize = 32.sp
                        )
                        Text(
                            text = stringResource(Res.string.no_movies),
                            style = MaterialTheme.typography.bodyMedium,
                            color = AppColors.onSurfaceVariant
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreenContent(
        uiState = HomeUiState(),
        navigateToMovieDetail = {}
    )
}