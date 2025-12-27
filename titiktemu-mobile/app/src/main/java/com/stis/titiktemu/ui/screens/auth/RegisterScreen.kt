package com.stis.titiktemu.ui.screens.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stis.titiktemu.ui.components.CustomButton
import com.stis.titiktemu.ui.components.CustomTextField
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
    val viewModel = AuthViewModel(context)
    val loginState by viewModel.loginState.collectAsStateWithLifecycle()

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var namaLengkap by remember { mutableStateOf("") }
    var jabatan by remember { mutableStateOf("Mahasiswa") }
    var nimNip by remember { mutableStateOf("") }
    var noHp by remember { mutableStateOf("") }
    var jabatanMenuExpanded by remember { mutableStateOf(false) }

    val jabatanOptions = listOf("Mahasiswa", "Dosen", "Tendik", "Cleaning Service", "Lainnya")

    LaunchedEffect(loginState) {
        if (loginState is Resource.Success) {
            onNavigateToHome()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Daftar", style = Typography.headlineMedium) }
            )
        }
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
            Text(text = "Jabatan", style = Typography.labelMedium, modifier = Modifier.fillMaxWidth())
            TextField(
                value = jabatan,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                readOnly = true
            )
            DropdownMenu(
                expanded = jabatanMenuExpanded,
                onDismissRequest = { jabatanMenuExpanded = false }
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

            CustomTextField(
                value = nimNip,
                onValueChange = { nimNip = it },
                label = "NIM/NIP (Optional)",
                modifier = Modifier.padding(bottom = 12.dp)
            )

            CustomTextField(
                value = noHp,
                onValueChange = { noHp = it },
                label = "Nomor HP",
                keyboardType = KeyboardType.Phone,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            CustomButton(
                text = "Daftar",
                onClick = {
                    viewModel.register(
                        username, email, password, namaLengkap, jabatan,
                        nimNip.ifEmpty { null }, noHp
                    )
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

            if (loginState is Resource.Error) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = (loginState as Resource.Error).message,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.error,
                    style = Typography.bodySmall
                )
            }
        }
    }
}
