package com.example.offlinetest.cats.domain.usecase

import com.example.offlinetest.cats.domain.repository.CatRepository

class SearchBreedsUseCase(private val repo: CatRepository) {
    suspend operator fun invoke(query: String) = repo.searchBreeds(query)
}
