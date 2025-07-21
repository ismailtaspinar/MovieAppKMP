package com.ismailtaspinar.movieAppKmp

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ismailtaspinar.movieAppKmp.navigation.LocalNavigator
import com.ismailtaspinar.movieAppKmp.navigation.Screen
import com.ismailtaspinar.movieAppKmp.navigation.bottomNavItems
import com.ismailtaspinar.movieAppKmp.ui.screens.FavoritesScreen
import com.ismailtaspinar.movieAppKmp.ui.screens.HomeScreen
import com.ismailtaspinar.movieAppKmp.ui.screens.MovieDetailScreen
import com.ismailtaspinar.movieAppKmp.ui.screens.SearchScreen
import com.ismailtaspinar.movieAppKmp.ui.theme.AppColors
import com.ismailtaspinar.movieAppKmp.utils.StatusBarController
import io.kamel.core.config.KamelConfig
import io.kamel.core.config.takeFrom
import io.kamel.image.config.LocalKamelConfig
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MainApp() {
    val kamelConfig = koinInject<KamelConfig>()

    CompositionLocalProvider(LocalKamelConfig provides KamelConfig {
        takeFrom(kamelConfig)
    }) {
        LaunchedEffect(Unit) {
            StatusBarController.setupSystemBars(
                topColorHex = "#0B0D17",
                bottomColorHex = "#1A1D2E"
            )
        }

        PreComposeApp {
            val navigator = rememberNavigator()

            CompositionLocalProvider(LocalNavigator provides navigator) {
                var currentScreen by remember { mutableStateOf(Screen.Home.route) }

                val bottomNavScreens = listOf(
                    Screen.Home.route,
                    Screen.Search.route,
                    Screen.Favorites.route
                )

                val gradientColors = listOf(
                    AppColors.gradientStart,
                    AppColors.gradientMiddle,
                    AppColors.gradientEnd
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = gradientColors,
                                startY = 0f,
                                endY = Float.POSITIVE_INFINITY
                            )
                        )
                ) {
                    Scaffold(
                        containerColor = Color.Transparent,
                        bottomBar = {
                            if (currentScreen in bottomNavScreens) {
                                ModernNavigationBar(
                                    currentScreen = currentScreen,
                                    onNavigate = { route ->
                                        currentScreen = route
                                        navigator.navigate(route)
                                    }
                                )
                            }
                        }
                    ) { paddingValues ->
                        NavHost(
                            navigator = navigator,
                            initialRoute = Screen.Home.route,
                            modifier = Modifier.padding(paddingValues)
                                .consumeWindowInsets(paddingValues),
                            navTransition = NavTransition()
                        ) {
                            scene(route = Screen.Home.route) {
                                currentScreen = Screen.Home.route
                                HomeScreen()
                            }

                            scene(route = Screen.Search.route) {
                                currentScreen = Screen.Search.route
                                SearchScreen()
                            }

                            scene(route = Screen.Favorites.route) {
                                currentScreen = Screen.Favorites.route
                                FavoritesScreen()
                            }

                            scene(route = Screen.MovieDetail.routePattern) { backStackEntry ->
                                val movieId: Int = backStackEntry.path<Int>("movieId") ?: 0
                                currentScreen = "movie_detail"
                                MovieDetailScreen(movieId = movieId)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ModernNavigationBar(
    currentScreen: String,
    onNavigate: (String) -> Unit
) {
    NavigationBar(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .clip(RoundedCornerShape(28.dp))
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(28.dp),
                ambientColor = AppColors.primary.copy(alpha = 0.1f),
                spotColor = AppColors.primary.copy(alpha = 0.2f)
            ),
        containerColor = AppColors.surface.copy(alpha = 0.95f),
        tonalElevation = 12.dp
    ) {
        bottomNavItems.forEach { item ->
            val isSelected = currentScreen == item.screen.route

            val iconScale by animateFloatAsState(
                targetValue = if (isSelected) 1.3f else 1.0f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                ),
                label = "iconScale"
            )

            val iconAlpha by animateFloatAsState(
                targetValue = if (isSelected) 1f else 0.7f,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                ),
                label = "iconAlpha"
            )

            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.screen.route) },
                icon = {
                    Box(
                        modifier = Modifier
                            .size(if (isSelected) 32.dp else 28.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (isSelected)
                                    AppColors.primary.copy(alpha = 0.15f)
                                else
                                    Color.Transparent
                            )
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = stringResource(item.title),
                            modifier = Modifier
                                .scale(iconScale)
                                .padding(4.dp),
                            tint = if (isSelected)
                                AppColors.primary
                            else
                                AppColors.onSurfaceVariant.copy(alpha = iconAlpha)
                        )
                    }
                },
                label = {
                    Text(
                        text = stringResource(item.title),
                        color = if (isSelected)
                            AppColors.primary
                        else
                            AppColors.onSurfaceSecondary,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium
                        )
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AppColors.primary,
                    selectedTextColor = AppColors.primary,
                    unselectedIconColor = AppColors.onSurfaceVariant,
                    unselectedTextColor = AppColors.onSurfaceSecondary,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}