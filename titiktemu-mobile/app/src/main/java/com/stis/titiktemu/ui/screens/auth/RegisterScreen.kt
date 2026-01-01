package com.stis.titiktemu.ui.screens.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stis.titiktemu.ui.components.CustomButton
import com.stis.titiktemu.ui.components.CustomDropdownField
import com.stis.titiktemu.ui.components.CustomTextField
import com.stis.titiktemu.ui.screens.ViewModelFactory
import com.stis.titiktemu.ui.theme.Primary
import com.stis.titiktemu.ui.theme.TextSecondary
import com.stis.titiktemu.ui.theme.Typography
import com.stis.titiktemu.util.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: AuthViewModel = viewModel(factory = ViewModelFactory(context))
    val loginState by viewModel.loginState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var namaLengkap by remember { mutableStateOf("") }
    var jabatan by remember { mutableStateOf("Mahasiswa") }
    var nimNip by remember { mutableStateOf("") }
    var noHp by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf<String?>(null) }
    var jabatanMenuExpanded by remember { mutableStateOf(false) }

    val jabatanOptions = listOf("Mahasiswa", "Dosen", "Tendik", "Cleaning Service", "Lainnya")

    LaunchedEffect(loginState) {
        when (loginState) {
            is Resource.Success -> {
                if ((loginState as Resource.Success).data.isNotEmpty()) {
                    // Show success message
                    snackbarHostState.showSnackbar(
                        message = "Registrasi berhasil! Silakan login untuk melanjutkan."
                    )
                    // Reset state before navigating
                    viewModel.resetLoginState()
                    // Navigate to login screen after showing success message
                    onNavigateToLogin()
                }
            }
            is Resource.Error -> {
                snackbarHostState.showSnackbar(
                    message = (loginState as Resource.Error).message
                )
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Daftar", style = Typography.headlineMedium) }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Buat Akun Baru",
                style = Typography.displayMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            CustomTextField(
                value = username,
                onValueChange = { username = it },
                label = "Username",
                modifier = Modifier.padding(bottom = 12.dp)
            )

            CustomTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                keyboardType = KeyboardType.Email,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            CustomTextField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                isPassword = true,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            CustomTextField(
                value = namaLengkap,
                onValueChange = { namaLengkap = it },
                label = "Nama Lengkap",
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Jabatan Dropdown
            CustomDropdownField(
                value = jabatan,
                onValueChange = { jabatan = it },
                label = "Jabatan",
                options = jabatanOptions,
                expanded = jabatanMenuExpanded,
                onExpandedChange = { jabatanMenuExpanded = it },
                modifier = Modifier.padding(bottom = 12.dp)
            )

            CustomTextField(
                value = nimNip,
                onValueChange = { nimNip = it },
                label = "NIM/NIP (Optional)",
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
                label = "Nomor HP",
                placeholder = "Contoh: 628123456789",
                keyboardType = KeyboardType.Phone,
                error = phoneError,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            CustomButton(
                text = "Daftar",
                onClick = {
                    if (!noHp.startsWith("62") || noHp.length < 10) {
                        phoneError = "Nomor harus dimulai dengan 62 dan minimal 10 digit"
                    } else {
                        viewModel.register(
                            username, email, password, namaLengkap, jabatan,
                            nimNip.ifEmpty { null }, noHp
                        )
                    }
                },
                isLoading = loginState is Resource.Loading
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Sudah punya akun? ",
                    style = Typography.bodySmall,
                    color = TextSecondary
                )
                Text(
                    text = "Login di sini",
                    style = Typography.bodySmall,
                    color = Primary,
                    modifier = Modifier.clickable { onNavigateToLogin() }
                )
            }
        }
    }
}
