package com.example.offlinetest.cats.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.offlinetest.cats.domain.usecase.ObserveFavoritesUseCase
import com.example.offlinetest.cats.domain.usecase.RefreshFavoritesUseCase
import com.example.offlinetest.cats.domain.usecase.ToggleFavoriteUseCase
import com.example.offlinetest.cats.presentation.state.FavoritesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class FavoritesViewModel(
    observeFavorites: ObserveFavoritesUseCase,
    private val refreshFavorites: RefreshFavoritesUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FavoritesUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            observeFavorites().collect { list ->
                _state.update { it.copy(favorites = list) }
            }
        }
    }

    fun onEnterFavorites() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null, isStale = false) }

            runCatching { refreshFavorites() }
                .onSuccess {
                    _state.update { it.copy(isLoading = false, isStale = false) }
                }
                .onFailure { e ->
                    val isOffline = e is IOException
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isStale = isOffline,
                            errorMessage = if (isOffline) null else (e.message ?: "Error")
                        )
                    }
                }
        }
    }

    fun onToggleFavorite(breedId: String) {
        val breed = _state.value.favorites.firstOrNull { it.id == breedId } ?: return
        viewModelScope.launch { toggleFavorite(breed) }
    }
}
