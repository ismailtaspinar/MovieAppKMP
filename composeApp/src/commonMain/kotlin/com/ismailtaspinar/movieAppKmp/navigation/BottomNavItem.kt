package com.ismailtaspinar.movieAppKmp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import movieappkmp.composeapp.generated.resources.Res
import movieappkmp.composeapp.generated.resources.favorites_tab
import movieappkmp.composeapp.generated.resources.home_tab
import movieappkmp.composeapp.generated.resources.search_tab
import org.jetbrains.compose.resources.StringResource

data class BottomNavItem(
    val screen: Screen,
    val title: StringResource,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(
        screen = Screen.Home,
        title = Res.string.home_tab,
        icon = Icons.Default.Home
    ),
    BottomNavItem(
        screen = Screen.Search,
        title = Res.string.search_tab,
        icon = Icons.Default.Search
    ),
    BottomNavItem(
        screen = Screen.Favorites,
        title = Res.string.favorites_tab,
        icon = Icons.Default.Favorite
    )
)