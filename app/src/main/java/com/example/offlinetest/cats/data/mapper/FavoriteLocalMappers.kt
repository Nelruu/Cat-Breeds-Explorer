package com.example.offlinetest.cats.data.mapper

import com.example.offlinetest.cats.data.local.entity.FavoriteBreedEntity
import com.example.offlinetest.cats.domain.model.CatBreed

fun CatBreed.toFavoriteEntity(now: Long, isAvailable: Boolean = true): FavoriteBreedEntity {
    return FavoriteBreedEntity(
        id = id,
        name = name,
        origin = origin,
        temperament = temperament,
        lifeSpan = lifeSpan,
        description = description,
        imageUrl = imageUrl,
        isAvailable = isAvailable,
        lastUpdatedAt = now
    )
}

fun FavoriteBreedEntity.toDomain(): CatBreed {
    return CatBreed(
        id = id,
        name = name,
        origin = origin,
        temperament = temperament,
        lifeSpan = lifeSpan,
        description = description,
        imageUrl = imageUrl,
        isAvailable = isAvailable,
        lastUpdatedAt = lastUpdatedAt
    )
}
