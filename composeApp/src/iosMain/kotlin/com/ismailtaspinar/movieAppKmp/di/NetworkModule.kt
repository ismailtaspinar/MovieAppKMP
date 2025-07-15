package com.ismailtaspinar.movieAppKmp.di

import com.ismailtaspinar.movieAppKmp.config.AppConfig
import io.ktor.client.*
import io.ktor.client.engine.darwin.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

actual fun createHttpClient(): HttpClient = HttpClient(Darwin) {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            isLenient = true
        })
    }
    defaultRequest {
        url.takeFrom(AppConfig.BASE_URL)
        header(HttpHeaders.Authorization, "Bearer ${AppConfig.API_KEY}")
        header(HttpHeaders.Accept, ContentType.Application.Json.toString())
    }
}