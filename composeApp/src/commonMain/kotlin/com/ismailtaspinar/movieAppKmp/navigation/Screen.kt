package com.ismailtaspinar.movieAppKmp.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Search : Screen("search")
    data object Favorites : Screen("favorites")

    data class MovieDetail(val movieId: Int) : Screen("movie/$movieId") {
        companion object {
            const val routePattern = "movie/{movieId}"
        }
    }
}