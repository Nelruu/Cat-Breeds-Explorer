package com.example.offlinetest.cats.domain.usecase

import com.example.offlinetest.cats.domain.repository.CatRepository

class RefreshFavoritesUseCase(private val repo: CatRepository) {
    suspend operator fun invoke() = repo.refreshFavorites()
}
