package com.example.offlinetest.cats.domain.usecase

import com.example.offlinetest.cats.domain.repository.CatRepository

class ObserveFavoritesUseCase(private val repo: CatRepository) {
    operator fun invoke() = repo.observeFavorites()
}