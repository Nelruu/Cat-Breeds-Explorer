package com.example.offlinetest.cats.presentation.state

import com.example.offlinetest.cats.domain.model.CatBreed

data class SearchUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val isOffline: Boolean = false,
    val errorMessage: String? = null,
    val results: List<CatBreed> = emptyList(),
    val favoriteIds: Set<String> = emptySet()
) {
    val isEmpty: Boolean get() = !isLoading && errorMessage == null && results.isEmpty() && query.isNotBlank()
}
