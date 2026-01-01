package com.stis.titiktemu.ui.screens.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.stis.titiktemu.ui.components.KategoriChip
import com.stis.titiktemu.ui.components.LoadingDialog
import com.stis.titiktemu.ui.components.StatusBadge
import com.stis.titiktemu.ui.screens.ViewModelFactory
import com.stis.titiktemu.ui.theme.Typography
import com.stis.titiktemu.util.Config
import com.stis.titiktemu.util.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailLaporanScreen(
    laporanId: Long,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: DetailViewModel = viewModel(factory = ViewModelFactory(context))
    val laporanState by viewModel.laporanState.collectAsStateWithLifecycle()
    val deleteState by viewModel.deleteState.collectAsStateWithLifecycle()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    
    // Listen for logout event
    LaunchedEffect(Unit) {
        viewModel.navigateToLogin.collect {
            onBack() // Navigate back when session expires
        }
    }
    
    // Listen for error messages
    LaunchedEffect(Unit) {
        viewModel.errorMessage.collect { error ->
            errorMessage = error
            showErrorDialog = true
        }
    }

    LaunchedEffect(laporanId) {
        viewModel.loadLaporanDetail(laporanId)
    }

    LaunchedEffect(deleteState) {
        if (deleteState is Resource.Success) {
            onDelete()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detail Laporan", style = Typography.headlineMedium) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            )
        }
    ) { paddingValues ->
        when (laporanState) {
            is Resource.Idle -> {
                // Initial state - no action needed
            }
            is Resource.Loading -> {
                LoadingDialog()
            }
            is Resource.Success -> {
                val laporan = (laporanState as Resource.Success).data
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Badges
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        StatusBadge(if (laporan.tipe == "HILANG") "🔴 Hilang" else "🟢 Ditemukan")
                        Spacer(modifier = Modifier.width(8.dp))
                        KategoriChip(laporan.kategori)
                        Spacer(modifier = Modifier.weight(1f))
                        StatusBadge(laporan.status)
                    }

                    // Title
                    Text(
                        text = laporan.judul,
                        style = Typography.displayMedium,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Description
                    Text(
                        text = laporan.deskripsi,
                        style = Typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Divider
                    androidx.compose.material3.Divider(modifier = Modifier.padding(vertical = 16.dp))

                    // Details
                    DetailRow("Lokasi", laporan.lokasi)
                    DetailRow("Tanggal", laporan.tanggalKejadian)

                    Spacer(modifier = Modifier.height(24.dp))

                    // Reporter Info
                    Text(
                        text = "Informasi Pelapor",
                        style = Typography.titleMedium,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            DetailRow("Nama", laporan.pelaporNama)
                            DetailRow("Jabatan", laporan.pelaporJabatan)

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = {
                                        val intent = Intent(Intent.ACTION_VIEW).apply {
                                            data = Uri.parse("https://wa.me/${laporan.pelaporNoHp}")
                                        }
                                        context.startActivity(intent)
                                    },
                                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                        containerColor = androidx.compose.ui.graphics.Color(0xFF25D366)
                                    )
                                ) {
                                    Text("💬", style = Typography.titleMedium)
                                }
                                Button(onClick = {
                                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                                        data = Uri.parse("mailto:${laporan.pelaporEmail}")
                                    }
                                    context.startActivity(intent)
                                }) {
                                    Icon(Icons.Default.Email, contentDescription = null)
                                }
                            }
                        }
                    }
                    
                    // Photo Section
                    laporan.fotoUrl?.let { fotoUrl ->
                        if (fotoUrl.isNotEmpty()) {
                            Text(
                                text = "Foto",
                                style = Typography.titleMedium,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        model = Config.SERVER_BASE + fotoUrl
                                    ),
                                    contentDescription = "Foto Laporan",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
            }
            is Resource.Error -> {
                Text((laporanState as Resource.Error).message)
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Hapus Laporan?") },
            text = { Text("Apakah Anda yakin ingin menghapus laporan ini?") },
            confirmButton = {
                Button(onClick = {
                    viewModel.deleteLaporan(laporanId)
                    showDeleteDialog = false
                }) {
                    Text("Hapus")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }
    
    // Error dialog
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text("Error") },
            text = { Text(errorMessage) },
            confirmButton = {
                Button(onClick = { showErrorDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = label, style = Typography.labelMedium, modifier = Modifier.weight(0.3f))
        Text(text = value, style = Typography.bodyMedium, modifier = Modifier.weight(0.7f))
    }
}