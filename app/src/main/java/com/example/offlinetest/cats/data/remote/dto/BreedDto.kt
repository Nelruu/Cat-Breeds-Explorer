package com.example.offlinetest.cats.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BreedDto(
    val id: String,
    val name: String,
    val origin: String? = null,
    val temperament: String? = null,
    @SerialName("life_span") val lifeSpan: String? = null,
    val description: String? = null,
    @SerialName("reference_image_id") val referenceImageId: String? = null
)
