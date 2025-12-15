package com.example.offlinetest.cats.presentation.state

import com.example.offlinetest.cats.domain.model.CatBreed

data class FavoritesUiState(
    val isLoading: Boolean = false,
    val isStale: Boolean = false,
    val errorMessage: String? = null,
    val favorites: List<CatBreed> = emptyList()
) {
    val isEmpty: Boolean get() = !isLoading && favorites.isEmpty()
}