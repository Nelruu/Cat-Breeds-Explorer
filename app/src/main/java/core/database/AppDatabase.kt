package com.example.offlinetest.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.offlinetest.cats.data.local.dao.FavoriteBreedDao
import com.example.offlinetest.cats.data.local.entity.FavoriteBreedEntity

@Database(
    entities = [
        FavoriteBreedEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteBreedDao(): FavoriteBreedDao
}
