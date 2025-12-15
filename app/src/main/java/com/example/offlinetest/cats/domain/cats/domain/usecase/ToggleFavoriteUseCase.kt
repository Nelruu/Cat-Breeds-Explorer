package com.example.offlinetest.cats.domain.usecase

import com.example.offlinetest.cats.domain.model.CatBreed
import com.example.offlinetest.cats.domain.repository.CatRepository

class ToggleFavoriteUseCase(private val repo: CatRepository) {
    suspend operator fun invoke(breed: CatBreed) = repo.toggleFavorite(breed)
}
