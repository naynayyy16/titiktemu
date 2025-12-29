package com.stis.titiktemu.ui.screens.edit

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.Card
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.stis.titiktemu.ui.components.CustomButton
import com.stis.titiktemu.ui.components.CustomTextField
import com.stis.titiktemu.ui.components.LoadingDialog
import com.stis.titiktemu.ui.screens.ViewModelFactory
import com.stis.titiktemu.ui.screens.edit.EditViewModel
import com.stis.titiktemu.ui.theme.Primary
import com.stis.titiktemu.ui.theme.Typography
import com.stis.titiktemu.util.Config
import com.stis.titiktemu.util.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditLaporanScreen(
    laporanId: Long,
    onBack: () -> Unit,
    onSuccess: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: EditViewModel = viewModel(factory = ViewModelFactory(context))
    val laporanState by viewModel.laporanState.collectAsStateWithLifecycle()
    val updateState by viewModel.updateState.collectAsStateWithLifecycle()

    var judul by remember { mutableStateOf("") }
    var deskripsi by remember { mutableStateOf("") }
    var kategori by remember { mutableStateOf("") }
    var lokasi by remember { mutableStateOf("") }
    var tanggalKejadian by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("AKTIF") }
    var fotoUrl by remember { mutableStateOf<String?>(null) }
    var newImageUri by remember { mutableStateOf<Uri?>(null) }
    
    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        newImageUri = uri
    }
    
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

    LaunchedEffect(laporanState) {
        if (laporanState is Resource.Success) {
            val laporan = (laporanState as Resource.Success).data
            judul = laporan.judul
            deskripsi = laporan.deskripsi
            kategori = laporan.kategori
            lokasi = laporan.lokasi
            tanggalKejadian = laporan.tanggalKejadian
            status = laporan.status
            fotoUrl = laporan.fotoUrl
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
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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

                // Photo Display (read-only)
                Text("Foto", style = Typography.labelMedium)
                
                // Show new image if selected, else show existing photo
                if (newImageUri != null) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(bottom = 12.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = newImageUri),
                            contentDescription = "Foto Baru",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                } else {
                    fotoUrl?.let { url ->
                        if (url.isNotEmpty()) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .padding(bottom = 12.dp),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        model = Config.SERVER_BASE + url
                                    ),
                                    contentDescription = "Foto Laporan",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
                
                // Button to change photo
                CustomButton(
                    text = if (newImageUri != null || (fotoUrl != null && fotoUrl!!.isNotEmpty())) "Ganti Foto" else "Tambah Foto",
                    onClick = { imagePickerLauncher.launch("image/*") },
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
                            laporanId, judul, deskripsi, kategori, lokasi, tanggalKejadian, status, newImageUri
                        )
                    },
                    isLoading = updateState is Resource.Loading
                )
            }
        }
    }
    
    // Error dialog
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            icon = { Icon(Icons.Default.Warning, contentDescription = null) },
            title = { Text("Warning", color = Color.Red) },
            text = { Text("Anda tidak memiliki akses pada laporan ini!") },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { showErrorDialog = false },
                        colors = ButtonDefaults.buttonColors(containerColor = Primary)
                    ) {
                        Text("OK")
                    }
                }
            }
        )
    }
}
