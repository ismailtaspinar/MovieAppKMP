package com.ismailtaspinar.movieAppKmp.di

import com.ismailtaspinar.movieAppKmp.config.AppConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.HttpHeaders
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

actual fun createHttpClient(): HttpClient = HttpClient(CIO) {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            isLenient = true
        })
    }
    defaultRequest {
        url.takeFrom(AppConfig.BASE_URL)
        header(HttpHeaders.Authorization, "Bearer ${AppConfig.API_KEY}")
        header(HttpHeaders.Accept, Json.toString())
    }
}