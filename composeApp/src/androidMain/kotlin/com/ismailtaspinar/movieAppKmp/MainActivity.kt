package com.ismailtaspinar.movieAppKmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        WindowCompat.getInsetsController(
            window, window.decorView
        ).isAppearanceLightStatusBars = false
        setContent {
            MainApp()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    MainApp()
}