package com.ismailtaspinar.movieAppKmp

import android.app.Application
import com.ismailtaspinar.movieAppKmp.di.initKoin

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}