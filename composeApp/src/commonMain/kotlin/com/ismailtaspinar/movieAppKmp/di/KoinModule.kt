package com.ismailtaspinar.movieAppKmp.di

import com.ismailtaspinar.movieAppKmp.data.api.MovieApi
import org.koin.dsl.module

val appModule = module {
    includes(kamelModule)
    single { createHttpClient() }
    single { MovieApi(get()) }
}