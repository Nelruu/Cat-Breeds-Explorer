package com.example.offlinetest.cats.domain.usecase

import com.example.offlinetest.cats.domain.repository.CatRepository

class GetBreedDetailUseCase(private val repo: CatRepository) {
    suspend operator fun invoke(id: String) = repo.getBreedDetail(id)
}
