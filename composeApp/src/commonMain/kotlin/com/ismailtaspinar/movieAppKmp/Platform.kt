package com.ismailtaspinar.movieAppKmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform