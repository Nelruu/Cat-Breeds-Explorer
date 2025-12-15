package com.example.offlinetest.cats.data.remote.api

import com.example.offlinetest.cats.data.remote.dto.BreedDto
import com.example.offlinetest.cats.data.remote.dto.ImageDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class CatApi(private val client: HttpClient) {

    suspend fun getAllBreeds(): List<BreedDto> =
        client.get("https://api.thecatapi.com/v1/breeds").body()

    suspend fun searchBreeds(query: String): List<BreedDto> =
        client.get("https://api.thecatapi.com/v1/breeds/search") {
            parameter("q", query)
        }.body()

    suspend fun getImage(imageId: String): ImageDto =
        client.get("https://api.thecatapi.com/v1/images/$imageId").body()
}
