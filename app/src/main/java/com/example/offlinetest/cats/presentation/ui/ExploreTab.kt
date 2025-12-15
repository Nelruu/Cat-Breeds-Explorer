package com.example.offlinetest.cats.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import com.example.offlinetest.cats.domain.model.CatBreed


@Composable
fun ExploreTab(
    state: com.example.offlinetest.cats.presentation.state.SearchUiState,
    onSearch: () -> Unit,
    onToggleFavorite: (String) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Button(onClick = onSearch) { Text("Buscar") }
    }

    when {
        state.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

        state.errorMessage != null -> ErrorState(
            title = "Ocurrió un error",
            message = state.errorMessage,
            actionText = "Reintentar",
            onAction = onSearch
        )

        state.isOffline -> OfflineState()

        state.isEmpty -> EmptyState(
            title = "Sin resultados",
            message = "No encontramos razas para “${state.query}”.",
            actionText = "Intentar de nuevo",
            onAction = onSearch
        )

        else -> {
            Column(Modifier.fillMaxSize()) {
                Text(
                    text = "${state.results.size} razas encontradas",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 140.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.results) { breed ->
                        val isFav = state.favoriteIds.contains(breed.id)
                        BreedCard(
                            breed = breed,
                            isFavorite = isFav,
                            onFavoriteClick = { onToggleFavorite(breed.id) },
                            onClick = { }
                        )
                    }
                }
            }
        }
    }
}
