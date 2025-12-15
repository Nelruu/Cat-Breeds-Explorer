package com.example.offlinetest.cats.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FavoritesTab(
    state: com.example.offlinetest.cats.presentation.state.FavoritesUiState,
    onToggleFavorite: (String) -> Unit,
    onGoExplore: () -> Unit
) {
    when {
        state.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

        state.errorMessage != null -> ErrorState(
            title = "No se pudieron actualizar favoritos",
            message = state.errorMessage,
            actionText = "Reintentar",
            onAction = { }
        )

        state.isEmpty -> EmptyState(
            title = "No tienes favoritos aún",
            message = "Explora las razas y marca tus favoritas.",
            actionText = "Explorar razas",
            onAction = onGoExplore
        )

        else -> {
            Column(Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("${state.favorites.size} favoritos", style = MaterialTheme.typography.titleMedium)
                }

                if (state.isStale) {
                    Text(
                        text = "Datos sin actualizar (sin conexión)",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 140.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.favorites) { breed ->
                        BreedCard(
                            breed = breed,
                            isFavorite = true,
                            onFavoriteClick = { onToggleFavorite(breed.id) },
                            onClick = { }
                        )
                    }
                }
            }
        }
    }
}
