package com.ismailtaspinar.movieAppKmp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val screen: Screen,
    val title: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(
        screen = Screen.Home,
        title = "Ana Sayfa",
        icon = Icons.Default.Home
    ),
    BottomNavItem(
        screen = Screen.Search,
        title = "Arama",
        icon = Icons.Default.Search
    ),
    BottomNavItem(
        screen = Screen.Favorites,
        title = "Favoriler",
        icon = Icons.Default.Favorite
    )
)