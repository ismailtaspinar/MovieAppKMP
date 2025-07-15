package com.ismailtaspinar.movieAppKmp.di

import org.koin.core.context.startKoin

actual fun initKoin() {
    startKoin {
        modules(appModule)
    }
}