@file:Suppress("DEPRECATION")

package com.example.offlinetest.core.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

import androidx.room.Room
import com.example.offlinetest.core.database.AppDatabase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext


val appModule = module {

    single {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        explicitNulls = false
                    }
                )
            }
        }
    }


    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    

    single { com.example.offlinetest.cats.data.remote.api.CatApi(get()) }
    single { get<com.example.offlinetest.core.database.AppDatabase>().favoriteBreedDao() }
    single<com.example.offlinetest.cats.domain.repository.CatRepository> {
        com.example.offlinetest.cats.data.repository.CatRepositoryImpl(get(), get())
    }

    factory { com.example.offlinetest.cats.domain.usecase.SearchBreedsUseCase(get()) }
    factory { com.example.offlinetest.cats.domain.usecase.GetBreedDetailUseCase(get()) }
    factory { com.example.offlinetest.cats.domain.usecase.ObserveFavoritesUseCase(get()) }
    factory { com.example.offlinetest.cats.domain.usecase.ToggleFavoriteUseCase(get()) }
    factory { com.example.offlinetest.cats.domain.usecase.RefreshFavoritesUseCase(get()) }

    viewModel { com.example.offlinetest.cats.presentation.viewmodel.CatSearchViewModel(get(), get(), get()) }
    viewModel { com.example.offlinetest.cats.presentation.viewmodel.FavoritesViewModel(get(), get(), get()) }

}
