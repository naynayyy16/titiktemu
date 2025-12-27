package com.stis.titiktemu.ui.screens.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stis.titiktemu.ui.components.CustomButton
import com.stis.titiktemu.ui.components.CustomTextField
import com.stis.titiktemu.ui.components.LoadingDialog
import com.stis.titiktemu.ui.screens.edit.EditViewModel
import com.stis.titiktemu.ui.theme.Typography
import com.stis.titiktemu.util.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditLaporanScreen(
    laporanId: Long,
    onBack: () -> Unit,
    onSuccess: () -> Unit
) {
    val context = LocalContext.current
    val viewModel = EditViewModel(context)
    val laporanState by viewModel.laporanState.collectAsStateWithLifecycle()
    val updateState by viewModel.updateState.collectAsStateWithLifecycle()

    var judul by remember { mutableStateOf("") }
    var deskripsi by remember { mutableStateOf("") }
    var kategori by remember { mutableStateOf("") }
    var lokasi by remember { mutableStateOf("") }
    var tanggalKejadian by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("AKTIF") }

    LaunchedEffect(laporanId) {
        viewModel.loadLaporanDetail(laporanId)
    }

    LaunchedEffect(laporanState) {
        if (laporanState is Resource.Success) {
            val laporan = (laporanState as Resource.Success).data
            judul = laporan.judul
            deskripsi = laporan.deskripsi
            kategori = laporan.kategori
            lokasi = laporan.lokasi
            tanggalKejadian = laporan.tanggalKejadian
            status = laporan.status
        }
    }

    LaunchedEffect(updateState) {
        if (updateState is Resource.Success) {
            onSuccess()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Edit Laporan", style = Typography.headlineMedium) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (laporanState is Resource.Loading) {
            LoadingDialog()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                CustomTextField(
                    value = judul,
                    onValueChange = { judul = it },
                    label = "Judul",
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                CustomTextField(
                    value = deskripsi,
                    onValueChange = { deskripsi = it },
                    label = "Deskripsi",
                    singleLine = false,
                    maxLines = 4,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                CustomTextField(
                    value = kategori,
                    onValueChange = { kategori = it },
                    label = "Kategori",
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                CustomTextField(
                    value = lokasi,
                    onValueChange = { lokasi = it },
                    label = "Lokasi",
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                CustomTextField(
                    value = tanggalKejadian,
                    onValueChange = { tanggalKejadian = it },
                    label = "Tanggal",
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Text("Status", style = Typography.labelMedium)
                var statusExpanded by remember { mutableStateOf(false) }
                TextField(
                    value = status,
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    readOnly = true
                )
                DropdownMenu(
                    expanded = statusExpanded,
                    onDismissRequest = { statusExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("AKTIF") },
                        onClick = { status = "AKTIF"; statusExpanded = false }
                    )
                    DropdownMenuItem(
                        text = { Text("SELESAI") },
                        onClick = { status = "SELESAI"; statusExpanded = false }
                    )
                }

                CustomButton(
                    text = "Update",
                    onClick = {
                        viewModel.updateLaporan(
                            laporanId, judul, deskripsi, kategori, lokasi, tanggalKejadian, status
                        )
                    },
                    isLoading = updateState is Resource.Loading
                )
            }
        }
    }
}
