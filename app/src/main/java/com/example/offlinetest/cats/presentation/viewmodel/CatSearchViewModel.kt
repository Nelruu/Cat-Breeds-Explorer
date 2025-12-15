package com.example.offlinetest.cats.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.offlinetest.cats.domain.usecase.ObserveFavoritesUseCase
import com.example.offlinetest.cats.domain.usecase.SearchBreedsUseCase
import com.example.offlinetest.cats.domain.usecase.ToggleFavoriteUseCase
import com.example.offlinetest.cats.presentation.state.SearchUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CatSearchViewModel(
    private val searchBreeds: SearchBreedsUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase,
    private val observeFavorites: ObserveFavoritesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SearchUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            observeFavorites().collect { favList ->
                val ids = favList.map { it.id }.toSet()
                _state.update { it.copy(favoriteIds = ids) }
            }
        }
    }

    fun onQueryChange(value: String) {
        _state.update { it.copy(query = value, errorMessage = null) }
    }

    fun onSearchClick() {
        val q = _state.value.query.trim()
        if (q.isEmpty()) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            runCatching { searchBreeds(q) }
                .onSuccess { list ->
                    _state.update { it.copy(isLoading = false, results = list) }
                }
                .onFailure { e ->
                    val offline = e is java.io.IOException
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isOffline = offline,
                            errorMessage = if (offline) null else (e.message ?: "Error")
                        )
                    }
                }

        }
    }

    fun onToggleFavorite(breedId: String) {
        val breed = _state.value.results.firstOrNull { it.id == breedId } ?: return
        viewModelScope.launch { toggleFavorite(breed) }
    }
}
