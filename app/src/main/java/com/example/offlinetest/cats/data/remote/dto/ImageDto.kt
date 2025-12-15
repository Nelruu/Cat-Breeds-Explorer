package com.example.offlinetest.cats.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ImageDto(
    val id: String,
    val url: String? = null
)