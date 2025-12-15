package com.example.offlinetest.cats.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.offlinetest.cats.data.local.entity.FavoriteBreedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteBreedDao {

    @Query("SELECT * FROM favorite_breeds ORDER BY name ASC")
    fun observeFavorites(): Flow<List<FavoriteBreedEntity>>

    @Query("SELECT * FROM favorite_breeds WHERE id = :id LIMIT 1")
    suspend fun findById(id: String): FavoriteBreedEntity?

    @Query("DELETE FROM favorite_breeds WHERE id = :id")
    suspend fun deleteById(id: String)

    @Upsert
    suspend fun upsert(entity: FavoriteBreedEntity)

    @Query("SELECT * FROM favorite_breeds")
    suspend fun getFavoritesOnce(): List<FavoriteBreedEntity>
}
