package com.example.offlinetest.cats.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_breeds")
data class FavoriteBreedEntity(
    @PrimaryKey val id: String,
    val name: String,
    val origin: String?,
    val temperament: String?,
    val lifeSpan: String?,
    val description: String?,
    val imageUrl: String?,

    val isAvailable: Boolean,
    val lastUpdatedAt: Long
)
