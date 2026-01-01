package com.stis.titiktemu.ui.screens.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stis.titiktemu.ui.components.CustomButton
import com.stis.titiktemu.ui.components.CustomTextField
import com.stis.titiktemu.ui.screens.ViewModelFactory
import com.stis.titiktemu.ui.theme.Typography
import com.stis.titiktemu.util.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val viewModel: ProfileViewModel = viewModel(factory = ViewModelFactory(context))
    val profileState by viewModel.profileState.collectAsStateWithLifecycle()
    val updateState by viewModel.updateState.collectAsStateWithLifecycle()

    var namaLengkap by remember { mutableStateOf("") }
    var jabatan by remember { mutableStateOf("") }
    var nimNip by remember { mutableStateOf("") }
    var noHp by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf<String?>(null) }
    var jabatanMenuExpanded by remember { mutableStateOf(false) }
    
    val jabatanOptions = listOf("Mahasiswa", "Dosen", "Tendik", "Cleaning Service", "Lainnya")
    
    // Listen for logout event
    LaunchedEffect(Unit) {
        viewModel.navigateToLogin.collect {
            onBack() // Navigate back when session expires
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    LaunchedEffect(profileState) {
        if (profileState is Resource.Success) {
            val user = (profileState as Resource.Success).data
            namaLengkap = user.namaLengkap
            jabatan = user.jabatan
            nimNip = user.nimNip ?: ""
            noHp = user.noHp
            email = user.email
        }
    }

    LaunchedEffect(updateState) {
        if (updateState is Resource.Success) {
            onBack()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Edit Profil", style = Typography.headlineMedium) },
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
            CustomTextField(
                value = namaLengkap,
                onValueChange = { namaLengkap = it },
                label = "Nama Lengkap",
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Jabatan Dropdown
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                OutlinedTextField(
                    value = jabatan,
                    onValueChange = {},
                    label = { Text("Jabatan", style = Typography.labelMedium) },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    enabled = false,
                    textStyle = Typography.bodyMedium
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { jabatanMenuExpanded = true }
                )
                DropdownMenu(
                    expanded = jabatanMenuExpanded,
                    onDismissRequest = { jabatanMenuExpanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    jabatanOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                jabatan = option
                                jabatanMenuExpanded = false
                            }
                        )
                    }
                }
            }

            CustomTextField(
                value = nimNip,
                onValueChange = { nimNip = it },
                label = "NIM/NIP",
                modifier = Modifier.padding(bottom = 12.dp)
            )

            CustomTextField(
                value = noHp,
                onValueChange = { 
                    noHp = it
                    phoneError = when {
                        it.isEmpty() -> null
                        !it.startsWith("62") -> "Nomor harus dimulai dengan 62"
                        it.length < 10 -> "Nomor terlalu pendek"
                        else -> null
                    }
                },
                label = "No HP",
                placeholder = "Contoh: 628123456789",
                error = phoneError,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            CustomTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                modifier = Modifier.padding(bottom = 24.dp)
            )

            CustomButton(
                text = "Simpan",
                onClick = {
                    if (!noHp.startsWith("62") || noHp.length < 10) {
                        phoneError = "Nomor harus dimulai dengan 62 dan minimal 10 digit"
                    } else {
                        viewModel.updateProfile(namaLengkap, jabatan, nimNip.ifEmpty { null }, noHp, email)
                    }
                },
                isLoading = updateState is Resource.Loading
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val viewModel: ProfileViewModel = viewModel(factory = ViewModelFactory(context))
    val passwordState by viewModel.passwordState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    // Handle success - show success message and navigate back
    LaunchedEffect(passwordState) {
        when (passwordState) {
            is Resource.Success -> {
                snackbarHostState.showSnackbar("Password berhasil diubah!")
                onBack()
            }
            is Resource.Error -> {
                snackbarHostState.showSnackbar(
                    (passwordState as Resource.Error).message
                )
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Ganti Password", style = Typography.headlineMedium) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            CustomTextField(
                value = oldPassword,
                onValueChange = { 
                    oldPassword = it
                    errorMessage = ""
                },
                label = "Password Lama",
                isPassword = true,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            CustomTextField(
                value = newPassword,
                onValueChange = { 
                    newPassword = it
                    errorMessage = ""
                },
                label = "Password Baru",
                isPassword = true,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            CustomTextField(
                value = confirmPassword,
                onValueChange = { 
                    confirmPassword = it
                    errorMessage = ""
                },
                label = "Konfirmasi Password",
                isPassword = true,
                error = if (confirmPassword.isNotEmpty() && confirmPassword != newPassword) 
                    "Password tidak sama" else null,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Show validation error
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.error,
                    style = Typography.bodySmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            } else {
                Spacer(modifier = Modifier.height(16.dp))
            }

            CustomButton(
                text = "Ubah Password",
                onClick = {
                    when {
                        oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty() -> {
                            errorMessage = "Semua field harus diisi"
                        }
                        newPassword.length < 6 -> {
                            errorMessage = "Password baru minimal 6 karakter"
                        }
                        newPassword != confirmPassword -> {
                            errorMessage = "Password baru dan konfirmasi tidak sama"
                        }
                        oldPassword == newPassword -> {
                            errorMessage = "Password baru harus berbeda dari password lama"
                        }
                        else -> {
                            errorMessage = ""
                            viewModel.changePassword(oldPassword, newPassword)
                        }
                    }
                },
                isLoading = passwordState is Resource.Loading
            )
        }
    }
}
