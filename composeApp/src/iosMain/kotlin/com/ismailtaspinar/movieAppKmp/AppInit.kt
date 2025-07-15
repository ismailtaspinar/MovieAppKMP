package com.ismailtaspinar.movieAppKmp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.ismailtaspinar.movieAppKmp.di.initKoin

@Composable
fun AppInit(content: @Composable () -> Unit) {
    LaunchedEffect(Unit) {
        initKoin()
    }
    content()
}