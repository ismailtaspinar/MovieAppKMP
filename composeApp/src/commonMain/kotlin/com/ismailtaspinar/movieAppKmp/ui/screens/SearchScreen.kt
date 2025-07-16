package com.ismailtaspinar.movieAppKmp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ismailtaspinar.movieAppKmp.data.model.Movie
import com.ismailtaspinar.movieAppKmp.navigation.LocalNavigator
import com.ismailtaspinar.movieAppKmp.navigation.Screen
import com.ismailtaspinar.movieAppKmp.ui.components.SearchMovieItem
import com.ismailtaspinar.movieAppKmp.ui.theme.AppColors
import com.ismailtaspinar.movieAppKmp.ui.viewModel.SearchUiState
import com.ismailtaspinar.movieAppKmp.ui.viewModel.SearchViewModel
import kotlinx.coroutines.delay
import moe.tlaster.precompose.viewmodel.viewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SearchScreen() {
    val navigator = LocalNavigator.current
    val viewModel = viewModel(SearchViewModel::class) { SearchViewModel() }
    val uiState by viewModel.uiState.collectAsState()

    SearchScreenContent(
        uiState = uiState,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onMovieClick = { movie ->
            navigator.navigate(Screen.MovieDetail(movie.id ?: 0).route)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchScreenContent(
    uiState: SearchUiState,
    onSearchQueryChange: (String) -> Unit,
    onMovieClick: (Movie) -> Unit
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
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
        ) {
            // Header Section
            Column(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "🔍 Film Ara",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    ),
                    color = AppColors.onSurface
                )
                Text(
                    text = "Favori filmlerinizi bulun",
                    style = MaterialTheme.typography.bodyLarge,
                    color = AppColors.onSurfaceVariant
                )
            }

            // Search TextField Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AppColors.surface
                ),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = onSearchQueryChange,
                    label = {
                        Text(
                            "Film adı girin...",
                            color = AppColors.onSurfaceVariant
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Ara",
                            tint = AppColors.primary
                        )
                    },
                    trailingIcon = {
                        if (uiState.searchQuery.isNotBlank()) {
                            IconButton(
                                onClick = { onSearchQueryChange("") }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Temizle",
                                    tint = AppColors.onSurfaceVariant
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AppColors.primary,
                        unfocusedBorderColor = AppColors.onSurfaceVariant.copy(alpha = 0.3f),
                        focusedTextColor = AppColors.onSurface,
                        unfocusedTextColor = AppColors.onSurface,
                        cursorColor = AppColors.primary
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(modifier = Modifier.weight(1f)) {
                when {
                    uiState.isSearching -> {
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
                                    text = "Aranıyor...",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = AppColors.onSurfaceVariant
                                )
                            }
                        }
                    }

                    uiState.searchQuery.isBlank() -> {
                        EmptySearchState()
                    }

                    uiState.searchResults.isEmpty() -> {
                        NoResultsState(searchQuery = uiState.searchQuery)
                    }

                    else -> {
                        SearchResultsList(
                            results = uiState.searchResults,
                            onMovieClick = onMovieClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptySearchState() {
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
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = AppColors.surface.copy(alpha = 0.7f)
                ),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "🎬",
                        fontSize = 64.sp
                    )
                    Text(
                        text = "Film Keşfetmeye Hazır mısın?",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = AppColors.onSurface,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Arama yapmak için yukarıdaki kutucuğa film adı girin",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppColors.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun NoResultsState(searchQuery: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = AppColors.surface.copy(alpha = 0.7f)
            ),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "😕",
                    fontSize = 64.sp
                )
                Text(
                    text = "Sonuç Bulunamadı",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = AppColors.onSurface,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "\"$searchQuery\" için film bulunamadı",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppColors.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Farklı anahtar kelimeler deneyin",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppColors.onSurfaceSecondary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
private fun SearchResultsList(
    results: List<Movie> = emptyList(),
    onMovieClick: (Movie) -> Unit = {}
) {
    Column {
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
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "📋 Arama Sonuçları",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = AppColors.onSurface
                )
                Surface(
                    color = AppColors.primary,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "${results.size}",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = AppColors.onPrimary
                    )
                }
            }
        }

        LazyColumn {
            itemsIndexed(results) { index, movie ->
                var isVisible by remember { mutableStateOf(false) }

                LaunchedEffect(movie) {
                    delay(index * 50L)
                    isVisible = true
                }

                val alpha by animateFloatAsState(
                    targetValue = if (isVisible) 1f else 0f,
                    animationSpec = tween(durationMillis = 500),
                    label = "itemAlpha"
                )

                Box(modifier = Modifier.alpha(alpha)) {
                    SearchMovieItem(
                        movie = movie,
                        onClick = { onMovieClick(movie) }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Preview()
@Composable
fun SearchScreenContentPreview_EmptyState() {
    SearchScreenContent(
        uiState = SearchUiState(
            searchQuery = "",
            searchResults = emptyList(),
            isSearching = false
        ),
        onSearchQueryChange = {},
        onMovieClick = {}
    )
}

@Preview()
@Composable
fun SearchScreenContentPreview_SearchingState() {
    SearchScreenContent(
        uiState = SearchUiState(
            searchQuery = "Inception",
            searchResults = emptyList(),
            isSearching = true
        ),
        onSearchQueryChange = {},
        onMovieClick = {}
    )
}

@Preview()
@Composable
fun SearchScreenContentPreview_ResultsState() {
    SearchScreenContent(
        uiState = SearchUiState(
            searchQuery = "Batman",
            searchResults = listOf(
                Movie(
                    id = 1,
                    title = "The Dark Knight",
                    overview = "Batman, Joker'in yarattığı kaosu durdurmaya çalışır.",
                    poster_path = "",
                    vote_average = 9.0,
                    release_date = "2008"
                ),
                Movie(
                    id = 2,
                    title = "Batman Begins",
                    overview = "Bruce Wayne'in Batman'e dönüşüm hikayesi.",
                    poster_path = "",
                    vote_average = 8.2,
                    release_date = "2005"
                ),
                Movie(
                    id = 3,
                    title = "The Batman",
                    overview = "Genç Batman, Riddler'ın bıraktığı ipuçlarını takip eder.",
                    poster_path = "",
                    vote_average = 7.8,
                    release_date = "2022"
                )
            ),
            isSearching = false
        ),
        onSearchQueryChange = {},
        onMovieClick = {}
    )
}

@Preview()
@Composable
fun SearchScreenContentPreview_NoResultsState() {
    SearchScreenContent(
        uiState = SearchUiState(
            searchQuery = "asdqwezxc",
            searchResults = emptyList(),
            isSearching = false
        ),
        onSearchQueryChange = {},
        onMovieClick = {}
    )
}