package com.ismailtaspinar.movieAppKmp.ui.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ismailtaspinar.movieAppKmp.data.model.Movie
import com.ismailtaspinar.movieAppKmp.ui.theme.AppColors
import com.ismailtaspinar.movieAppKmp.ui.viewModel.MovieDetailViewModel
import com.ismailtaspinar.movieAppKmp.utils.extension.formatToOneDecimal
import io.kamel.core.config.KamelConfig
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.kamel.image.config.LocalKamelConfig
import io.ktor.http.Url
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.viewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    movieId: Int,
    navigator: Navigator,
    viewModel: MovieDetailViewModel = viewModel { MovieDetailViewModel(movieId) }
) {
    val uiState by viewModel.uiState.collectAsState()
    var isFabPressed by remember { mutableStateOf(false) }

    val fabScale by animateFloatAsState(
        targetValue = if (isFabPressed) 0.9f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        label = "fabScale"
    )

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
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { },
                    navigationIcon = {
                        IconButton(
                            onClick = { navigator.goBack() },
                            modifier = Modifier
                                .padding(8.dp)
                                .background(
                                    color = AppColors.surface.copy(alpha = 0.8f),
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Geri",
                                tint = AppColors.onSurface
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            },
            floatingActionButton = {
                uiState.movie?.let { movie ->
                    FloatingActionButton(
                        onClick = {
                            isFabPressed = true
                            viewModel.toggleFavorite()
                        },
                        modifier = Modifier
                            .scale(fabScale)
                            .shadow(
                                elevation = 16.dp,
                                shape = CircleShape,
                                ambientColor = if (uiState.isFavorite)
                                    AppColors.error.copy(alpha = 0.3f)
                                else
                                    AppColors.primary.copy(alpha = 0.3f),
                                spotColor = if (uiState.isFavorite)
                                    AppColors.error.copy(alpha = 0.4f)
                                else
                                    AppColors.primary.copy(alpha = 0.4f)
                            ),
                        containerColor = if (uiState.isFavorite)
                            AppColors.error
                        else
                            AppColors.primary,
                        contentColor = Color.White
                    ) {
                        Icon(
                            imageVector = if (uiState.isFavorite)
                                Icons.Default.Favorite
                            else
                                Icons.Default.FavoriteBorder,
                            contentDescription = if (uiState.isFavorite)
                                "Favorilerden Çıkar"
                            else
                                "Favorilere Ekle",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        ) { paddingValues ->
            when {
                uiState.isLoading -> {
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
                                text = "Film yükleniyor...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = AppColors.onSurfaceVariant
                            )
                        }
                    }
                }

                uiState.movie != null -> {
                    MovieDetailContent(
                        movie = uiState.movie!!,
                        modifier = Modifier.padding(paddingValues)
                    )
                }

                else -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "🎬",
                                fontSize = 64.sp
                            )
                            Text(
                                text = "Film bulunamadı",
                                style = MaterialTheme.typography.titleLarge,
                                color = AppColors.onSurfaceVariant
                            )
                            Text(
                                text = "Bu film mevcut değil veya kaldırılmış olabilir",
                                style = MaterialTheme.typography.bodyMedium,
                                color = AppColors.onSurfaceSecondary,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieDetailContent(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    val kamelConfig = koinInject<KamelConfig>()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
        ) {
            CompositionLocalProvider(LocalKamelConfig provides kamelConfig) {
                KamelImage(
                    resource = asyncPainterResource(Url(movie.backdropUrl)),
                    contentDescription = movie.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    onLoading = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(AppColors.surfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(40.dp),
                                    color = AppColors.primary,
                                    strokeWidth = 3.dp
                                )
                                Text(
                                    text = "Resim yükleniyor...",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = AppColors.onSurfaceVariant
                                )
                            }
                        }
                    },
                    onFailure = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(AppColors.surfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "🎬",
                                    fontSize = 48.sp,
                                    color = AppColors.onSurfaceVariant
                                )
                                Text(
                                    text = "Resim yüklenemedi",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = AppColors.onSurfaceVariant
                                )
                            }
                        }
                    }
                )
            }

            // Enhanced gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                AppColors.background.copy(alpha = 0.05f),
                                AppColors.background.copy(alpha = 0.15f),
                                AppColors.background.copy(alpha = 0.25f)
                            ),
                            startY = 0f,
                            endY = 350.dp.value
                        )
                    )
            )
        }

        // Movie Information
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            // Enhanced Title
            Text(
                text = movie.title ?: "",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    lineHeight = 36.sp
                ),
                color = AppColors.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Enhanced Rating and metadata row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Enhanced Rating Badge
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = AppColors.primary,
                    shadowElevation = 8.dp,
                    modifier = Modifier.shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(20.dp),
                        ambientColor = AppColors.primary.copy(alpha = 0.3f),
                        spotColor = AppColors.primary.copy(alpha = 0.4f)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "⭐",
                            fontSize = 18.sp
                        )
                        Text(
                            text = movie.vote_average?.formatToOneDecimal() ?: "N/A",
                            color = AppColors.onPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }

                // Enhanced Release Date Badge
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = AppColors.secondary.copy(alpha = 0.15f),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        AppColors.secondary.copy(alpha = 0.4f)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "📅",
                            fontSize = 16.sp
                        )
                        Text(
                            text = movie.release_date ?: "N/A",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = AppColors.secondary
                        )
                    }
                }
            }

            // Vote Count Badge
            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = AppColors.accent.copy(alpha = 0.15f),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    AppColors.accent.copy(alpha = 0.4f)
                )
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "👥",
                        fontSize = 14.sp
                    )
                    Text(
                        text = "${movie.vote_count} kişi oyladı",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = AppColors.accent
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Enhanced Overview Section
            Text(
                text = "📖 Film Özeti",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                ),
                color = AppColors.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = AppColors.surface
                ),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Text(
                    text = movie.overview ?: "Bu film için özet bilgisi mevcut değil.",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        lineHeight = 26.sp,
                        fontSize = 16.sp
                    ),
                    color = AppColors.onSurface,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Enhanced Additional Info Card
            Text(
                text = "ℹ️ Film Detayları",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                ),
                color = AppColors.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = AppColors.surfaceElevated
                ),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    InfoRow("🎭 Orijinal Başlık", movie.original_title ?: "N/A")
                    InfoRow("🌍 Dil", movie.original_language?.uppercase() ?: "N/A")
                    InfoRow("📈 Popülerlik", movie.popularity?.formatToOneDecimal() ?: "N/A")
                    InfoRow("🔞 Yetişkin İçeriği", if (movie.adult == true) "Evet" else "Hayır")
                }
            }

            // Add bottom padding for FAB
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium
            ),
            color = AppColors.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = AppColors.onSurface,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}