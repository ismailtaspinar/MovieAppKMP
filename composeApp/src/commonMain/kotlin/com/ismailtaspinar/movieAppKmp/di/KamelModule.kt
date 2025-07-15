package com.ismailtaspinar.movieAppKmp.di

import io.kamel.core.config.KamelConfig
import io.kamel.core.config.httpUrlFetcher
import io.kamel.image.config.imageBitmapDecoder
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.http.isSuccess
import org.koin.dsl.module

val kamelModule = module {
    single {
        KamelConfig {
            imageBitmapDecoder()
            httpUrlFetcher {
                httpCache(10 * 1024 * 1024)

                install(HttpRequestRetry) {
                    maxRetries = 3
                    retryIf { _, response ->
                        !response.status.isSuccess()
                    }
                }
            }
        }
    }
}