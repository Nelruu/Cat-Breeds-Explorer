package com.example.offlinetest.cats.data.mapper

import com.example.offlinetest.cats.data.remote.dto.BreedDto
import com.example.offlinetest.cats.domain.model.CatBreed

fun BreedDto.toDomain(imageUrl: String?): CatBreed = CatBreed(
    id = id,
    name = name,
    origin = origin,
    temperament = temperament,
    lifeSpan = lifeSpan,
    description = description,
    imageUrl = imageUrl
)
