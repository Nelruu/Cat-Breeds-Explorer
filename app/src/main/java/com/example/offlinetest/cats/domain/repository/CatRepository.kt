package com.example.offlinetest.cats.domain.repository

import com.example.offlinetest.cats.domain.model.CatBreed
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    suspend fun searchBreeds(query: String): List<CatBreed>
    suspend fun getBreedDetail(id: String): CatBreed?

    fun observeFavorites(): Flow<List<CatBreed>>
    suspend fun toggleFavorite(breed: CatBreed)
    suspend fun refreshFavorites()
}
