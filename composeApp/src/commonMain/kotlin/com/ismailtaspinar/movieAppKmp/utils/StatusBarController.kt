package com.ismailtaspinar.movieAppKmp.utils

expect object StatusBarController {
    fun setStatusBarColor(colorHex: String)
    fun setNavigationBarColor(colorHex: String)
    fun setupSystemBars(topColorHex: String, bottomColorHex: String)
}