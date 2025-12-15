package com.example.offlinetest.cats.domain.model

data class CatBreed(
    val id: String,
    val name: String,
    val origin: String? = null,
    val temperament: String? = null,
    val lifeSpan: String? = null,
    val description: String? = null,
    val imageUrl: String? = null,


    val isAvailable: Boolean = true,
    val lastUpdatedAt: Long? = null
)
