package com.example.offlinetest.cats.data.repository

import com.example.offlinetest.cats.data.local.dao.FavoriteBreedDao
import com.example.offlinetest.cats.data.mapper.toDomain
import com.example.offlinetest.cats.data.mapper.toFavoriteEntity
import com.example.offlinetest.cats.data.mapper.toDomain as favToDomain
import com.example.offlinetest.cats.data.remote.api.CatApi
import com.example.offlinetest.cats.domain.model.CatBreed
import com.example.offlinetest.cats.domain.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CatRepositoryImpl(
    private val api: CatApi,
    private val favDao: FavoriteBreedDao
) : CatRepository {

    override suspend fun searchBreeds(query: String): List<CatBreed> {
        val q = query.trim()
        if (q.isEmpty()) return emptyList()

        val all = api.getAllBreeds()

        val filtered = all.filter { dto ->
            val name = dto.name ?: ""
            val origin = dto.origin ?: ""
            val temp = dto.temperament ?: ""
            name.contains(q, ignoreCase = true) ||
                    origin.contains(q, ignoreCase = true) ||
                    temp.contains(q, ignoreCase = true)
        }

        return filtered.take(50).map { dto ->
            val img = dto.referenceImageId?.let { api.getImage(it).url }
            dto.toDomain(img)
        }
    }

    override suspend fun getBreedDetail(id: String): CatBreed? {
        val all = api.getAllBreeds()
        val dto = all.firstOrNull { it.id == id } ?: return null
        val img = dto.referenceImageId?.let { api.getImage(it).url }
        return dto.toDomain(img)
    }

    override fun observeFavorites(): Flow<List<CatBreed>> {
        return favDao.observeFavorites().map { list ->
            list.map { it.favToDomain() }
        }
    }

    override suspend fun toggleFavorite(breed: CatBreed) {
        val existing = favDao.findById(breed.id)
        if (existing == null) {
            favDao.upsert(breed.toFavoriteEntity(now = System.currentTimeMillis()))
        } else {
            favDao.deleteById(breed.id)
        }
    }

    override suspend fun refreshFavorites() {
        val favorites = favDao.getFavoritesOnce()
        if (favorites.isEmpty()) return

        val now = System.currentTimeMillis()

        val all = api.getAllBreeds()

        favorites.forEach { fav ->
            val dto = all.firstOrNull { it.id == fav.id }

            if (dto == null) {
                favDao.upsert(
                    fav.copy(
                        isAvailable = false,
                        lastUpdatedAt = now
                    )
                )
                return@forEach
            }

            val img = dto.referenceImageId?.let { api.getImage(it).url }
            val updated = dto.toDomain(img)

            favDao.upsert(
                updated.toFavoriteEntity(
                    now = now,
                    isAvailable = true
                )
            )
        }
    }
}
