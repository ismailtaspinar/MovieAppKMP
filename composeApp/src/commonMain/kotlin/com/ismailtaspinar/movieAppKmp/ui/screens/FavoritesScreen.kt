package com.ismailtaspinar.movieAppKmp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ismailtaspinar.movieAppKmp.data.model.Movie
import com.ismailtaspinar.movieAppKmp.navigation.LocalNavigator
import com.ismailtaspinar.movieAppKmp.navigation.Screen
import com.ismailtaspinar.movieAppKmp.ui.components.MovieCard
import com.ismailtaspinar.movieAppKmp.ui.theme.AppColors
import com.ismailtaspinar.movieAppKmp.ui.viewModel.FavoritesUiState
import com.ismailtaspinar.movieAppKmp.ui.viewModel.FavoritesViewModel
import kotlinx.coroutines.delay
import moe.tlaster.precompose.viewmodel.viewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen() {
    val navigator = LocalNavigator.current
    val viewModel: FavoritesViewModel = viewModel { FavoritesViewModel() }
    val uiState by viewModel.uiState.collectAsState()

    FavoritesScreenContent(
        uiState = uiState,
        removeFromFavorites = viewModel::removeFromFavorites,
        navigateToMovieDetail = { movieId ->
            navigator.navigate(Screen.MovieDetail(movieId).route)
        }
    )
}

@Composable
internal fun FavoritesScreenContent(
    uiState: FavoritesUiState = FavoritesUiState(),
    removeFromFavorites: (Int) -> Unit = {},
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
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = AppColors.error,
                        modifier = Modifier.size(32.dp)
                    )
                    Text(
                        text = "Favori Filmlerim",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp
                        ),
                        color = AppColors.onSurface
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Beğendiğiniz filmler",
                        style = MaterialTheme.typography.bodyLarge,
                        color = AppColors.onSurfaceVariant
                    )

                    if (uiState.favoriteMovies.isNotEmpty()) {
                        Surface(
                            color = AppColors.primary,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "${uiState.favoriteMovies.size}",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = AppColors.onPrimary
                            )
                        }
                    }
                }
            }

            if (uiState.favoriteMovies.isEmpty()) {
                EmptyFavoritesState()
            } else {
                FavoriteMoviesGrid(
                    movies = uiState.favoriteMovies,
                    onMovieClick = { movie ->
                        navigateToMovieDetail(movie.id ?: 0)
                    },
                    onRemoveFromFavorites = { movieId ->
                        removeFromFavorites(movieId)
                    }
                )
            }
        }
    }
}

@Composable
private fun EmptyFavoritesState() {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(800)) + slideInVertically(),
        exit = fadeOut() + slideOutVertically()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = AppColors.surface.copy(alpha = 0.7f)
                ),
                shape = RoundedCornerShape(28.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Animated heart
                    var heartScale by remember { mutableStateOf(1f) }

                    LaunchedEffect(Unit) {
                        while (true) {
                            heartScale = 1.2f
                            delay(1000)
                            heartScale = 1f
                            delay(1000)
                        }
                    }

                    val animatedScale by animateFloatAsState(
                        targetValue = heartScale,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        ),
                        label = "heartScale"
                    )

                    Text(
                        text = "💔",
                        fontSize = 72.sp,
                        modifier = Modifier.scale(animatedScale)
                    )

                    Text(
                        text = "Henüz Favori Filminiz Yok",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        ),
                        color = AppColors.onSurface,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Film detay sayfalarından kalp butonuna tıklayarak favorilerinize ekleyebilirsiniz",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            lineHeight = 22.sp
                        ),
                        color = AppColors.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )

                    // Animated instruction
                    Surface(
                        color = AppColors.primary.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(16.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            AppColors.primary.copy(alpha = 0.3f)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                tint = AppColors.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Filmleri keşfetmeye başlayın!",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = AppColors.primary
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FavoriteMoviesGrid(
    movies: List<Movie>,
    onMovieClick: (Movie) -> Unit,
    onRemoveFromFavorites: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(movies) { index, movie ->
            var isVisible by remember { mutableStateOf(false) }
            var showDeleteDialog by remember { mutableStateOf(false) }

            LaunchedEffect(movie) {
                delay(index * 100L) // Staggered animation
                isVisible = true
            }

            val alpha by animateFloatAsState(
                targetValue = if (isVisible) 1f else 0f,
                animationSpec = tween(durationMillis = 600),
                label = "itemAlpha"
            )

            Box(modifier = Modifier.alpha(alpha)) {
                MovieCard(
                    movie = movie,
                    onClick = { onMovieClick(movie) }
                )

                Surface(
                    onClick = { showDeleteDialog = true },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                    color = AppColors.error.copy(alpha = 0.9f),
                    shape = CircleShape,
                    shadowElevation = 6.dp
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Favorilerden Çıkar",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(20.dp)
                    )
                }

                if (showDeleteDialog) {
                    AlertDialog(
                        onDismissRequest = { showDeleteDialog = false },
                        title = {
                            Text(
                                text = "🗑️ Favorilerden Çıkar",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = AppColors.onSurface
                            )
                        },
                        text = {
                            Text(
                                text = "\"${movie.title}\" filmini favorilerden çıkarmak istediğinizden emin misiniz?",
                                style = MaterialTheme.typography.bodyMedium,
                                color = AppColors.onSurfaceVariant
                            )
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    onRemoveFromFavorites(movie.id ?: 0)
                                    showDeleteDialog = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AppColors.error
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "Çıkar",
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = { showDeleteDialog = false },
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "İptal",
                                    color = AppColors.onSurfaceVariant,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        },
                        containerColor = AppColors.surface,
                        shape = RoundedCornerShape(20.dp)
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Preview
@Composable
fun FavoritesScreenPreview() {
    FavoritesScreenContent(
        uiState = FavoritesUiState(),
        removeFromFavorites = {},
        navigateToMovieDetail = {}
    )
}