package com.example.offlinetest.cats.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.offlinetest.cats.presentation.viewmodel.CatSearchViewModel
import com.example.offlinetest.cats.presentation.viewmodel.FavoritesViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatsHomeScreen(
    searchVm: CatSearchViewModel = koinViewModel(),
    favVm: FavoritesViewModel = koinViewModel()
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    val searchState by searchVm.state.collectAsState()
    val favState by favVm.state.collectAsState()

    // Auto-refresh al entrar a Favoritos
    LaunchedEffect(selectedTab) {
        if (selectedTab == 1) favVm.onEnterFavorites()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cat Breeds Explorer") },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Info, contentDescription = "Info")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Barra de búsqueda (se ve en ambos tabs en tu mock)
            OutlinedTextField(
                value = searchState.query,
                onValueChange = searchVm::onQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                placeholder = { Text("Buscar globalmente…") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                singleLine = true
            )

            TabRow(selectedTabIndex = selectedTab) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Explorar") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Mis Favoritos") }
                )
            }

            when (selectedTab) {
                0 -> ExploreTab(
                    state = searchState,
                    onSearch = searchVm::onSearchClick,
                    onToggleFavorite = searchVm::onToggleFavorite
                )

                1 -> FavoritesTab(
                    state = favState,
                    onToggleFavorite = favVm::onToggleFavorite,
                    onGoExplore = { selectedTab = 0 }
                )
            }
        }
    }
}
