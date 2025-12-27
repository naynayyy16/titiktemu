package com.stis.titiktemu.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stis.titiktemu.ui.components.EmptyState
import com.stis.titiktemu.ui.components.LaporanCard
import com.stis.titiktemu.ui.components.LoadingDialog
import com.stis.titiktemu.ui.theme.Primary
import com.stis.titiktemu.ui.theme.Typography
import com.stis.titiktemu.util.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onDetailClick: (Long) -> Unit,
    onCreateClick: () -> Unit,
    onProfileClick: () -> Unit,
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    val viewModel = HomeViewModel(context)
    val laporanState by viewModel.laporanState.collectAsStateWithLifecycle()
    val selectedFilter by viewModel.selectedFilter.collectAsStateWithLifecycle()

    var showFilterMenu by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Titik Temu", style = Typography.headlineMedium) },
                actions = {
                    IconButton(onClick = { onProfileClick() }) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateClick,
                containerColor = Primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Buat Laporan")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Filter Tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    label = "Semua",
                    selected = selectedFilter == null,
                    onClick = { viewModel.filterByTipe(null) }
                )
                FilterChip(
                    label = "Hilang",
                    selected = selectedFilter == "HILANG",
                    onClick = { viewModel.filterByTipe("HILANG") }
                )
                FilterChip(
                    label = "Ditemukan",
                    selected = selectedFilter == "TEMUKAN",
                    onClick = { viewModel.filterByTipe("TEMUKAN") }
                )
            }

            // Content
            when (laporanState) {
                is Resource.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingDialog()
                    }
                }
                is Resource.Success -> {
                    val data = (laporanState as Resource.Success).data
                    if (data.isEmpty()) {
                        EmptyState("Tidak ada laporan")
                    } else {
                        LazyColumn {
                            items(data) { laporan ->
                                LaporanCard(
                                    laporan = laporan,
                                    onClick = { onDetailClick(laporan.id) }
                                )
                            }
                        }
                    }
                }
                is Resource.Error -> {
                    EmptyState((laporanState as Resource.Error).message)
                }
            }
        }
    }
}

@Composable
fun FilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    androidx.compose.material3.AssistChip(
        onClick = onClick,
        label = { Text(label, style = Typography.labelSmall) },
        modifier = Modifier,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
    )
}
