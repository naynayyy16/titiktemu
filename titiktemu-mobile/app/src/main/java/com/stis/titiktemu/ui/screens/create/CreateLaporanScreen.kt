package com.stis.titiktemu.ui.screens.create

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stis.titiktemu.ui.components.CustomButton
import com.stis.titiktemu.ui.components.CustomDropdownField
import com.stis.titiktemu.ui.components.CustomTextField
import com.stis.titiktemu.ui.screens.ViewModelFactory
import com.stis.titiktemu.ui.screens.create.CreateViewModel
import com.stis.titiktemu.ui.theme.Typography
import com.stis.titiktemu.util.Resource
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateLaporanScreen(
    onBack: () -> Unit,
    onSuccess: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: CreateViewModel = viewModel(factory = ViewModelFactory(context))
    val createState by viewModel.createState.collectAsStateWithLifecycle()
    var tipe by remember { mutableStateOf("HILANG") }
    var kategori by remember { mutableStateOf("ELEKTRONIK") }
    var judul by remember { mutableStateOf("") }
    var deskripsi by remember { mutableStateOf("") }
    var lokasi by remember { mutableStateOf("") }
    var tanggalKejadian by remember { mutableStateOf(LocalDate.now().toString()) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var kategoriExpanded by remember { mutableStateOf(false) }

    val kategoriOptions = listOf(
        "ELEKTRONIK", "ALAT_TULIS", "AKSESORIS_PRIBADI", "ALAT_MAKAN", "DOKUMEN", "ATRIBUT_KAMPUS", "LAINNYA"
    )

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    
    // Listen for logout event
    LaunchedEffect(Unit) {
        viewModel.navigateToLogin.collect {
            onBack() // Navigate back when session expires
        }
    }

    LaunchedEffect(createState) {
        when (createState) {
            is Resource.Success -> {
                onSuccess()
            }
            is Resource.Error -> {
                errorMessage = (createState as Resource.Error).message ?: "Terjadi kesalahan"
                showErrorDialog = true
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Buat Laporan", style = Typography.headlineMedium) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Tipe Selection
            Text("Tipe Laporan", style = Typography.labelMedium)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                RadioButton(
                    selected = tipe == "HILANG",
                    onClick = { tipe = "HILANG" }
                )
                Text("Hilang", modifier = Modifier.align(Alignment.CenterVertically))
                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(
                    selected = tipe == "TEMUKAN",
                    onClick = { tipe = "TEMUKAN" }
                )
                Text("Ditemukan", modifier = Modifier.align(Alignment.CenterVertically))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Kategori Dropdown
            CustomDropdownField(
                value = kategori,
                onValueChange = { kategori = it },
                label = "Kategori",
                options = kategoriOptions,
                expanded = kategoriExpanded,
                onExpandedChange = { kategoriExpanded = it },
                modifier = Modifier.padding(bottom = 12.dp)
            )

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
                value = lokasi,
                onValueChange = { lokasi = it },
                label = "Lokasi",
                modifier = Modifier.padding(bottom = 12.dp)
            )

            CustomTextField(
                value = tanggalKejadian,
                onValueChange = { tanggalKejadian = it },
                label = "Tanggal Kejadian",
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Image Picker
            Text("Foto (Opsional)", style = Typography.labelMedium)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(bottom = 24.dp)
                    .border(
                        width = 1.dp,
                        color = androidx.compose.ui.graphics.Color.Gray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { imagePickerLauncher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (selectedImageUri != null) {
                    androidx.compose.foundation.Image(
                        painter = coil.compose.rememberAsyncImagePainter(selectedImageUri),
                        contentDescription = "Selected Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Photo",
                            modifier = Modifier.size(48.dp),
                            tint = androidx.compose.ui.graphics.Color.Gray
                        )
                        Text(
                            "Tap untuk pilih foto",
                            style = Typography.bodySmall,
                            color = androidx.compose.ui.graphics.Color.Gray
                        )
                    }
                }
            }

            CustomButton(
                text = "Simpan",
                onClick = {
                    viewModel.createLaporan(tipe, judul, deskripsi, kategori, lokasi, tanggalKejadian, selectedImageUri)
                },
                isLoading = createState is Resource.Loading
            )

            if (showErrorDialog) {
                androidx.compose.material3.AlertDialog(
                    onDismissRequest = { showErrorDialog = false },
                    title = { Text("Error") },
                    text = { Text(errorMessage) },
                    confirmButton = {
                        androidx.compose.material3.Button(
                            onClick = { showErrorDialog = false }
                        ) {
                            Text("OK")
                        }
                    }
                )
            }

            if (createState is Resource.Loading) {
                androidx.compose.material3.AlertDialog(
                    onDismissRequest = {},
                    text = {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            androidx.compose.material3.CircularProgressIndicator()
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Mengirim laporan...")
                        }
                    },
                    confirmButton = {}
                )
            }
        }
    }
}
