package com.ismailtaspinar.movieAppKmp.navigation

import androidx.compose.runtime.compositionLocalOf
import moe.tlaster.precompose.navigation.Navigator

val LocalNavigator = compositionLocalOf<Navigator> {
    error("No Navigator provided")
}