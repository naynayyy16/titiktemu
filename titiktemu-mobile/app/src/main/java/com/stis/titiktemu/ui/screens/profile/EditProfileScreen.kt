package com.stis.titiktemu.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stis.titiktemu.ui.components.CustomButton
import com.stis.titiktemu.ui.components.CustomTextField
import com.stis.titiktemu.ui.theme.Typography
import com.stis.titiktemu.util.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val viewModel = ProfileViewModel(context)
    val profileState by viewModel.profileState.collectAsStateWithLifecycle()
    val updateState by viewModel.updateState.collectAsStateWithLifecycle()

    var namaLengkap by remember { mutableStateOf("") }
    var jabatan by remember { mutableStateOf("") }
    var nimNip by remember { mutableStateOf("") }
    var noHp by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

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

            CustomTextField(
                value = jabatan,
                onValueChange = { jabatan = it },
                label = "Jabatan",
                modifier = Modifier.padding(bottom = 12.dp)
            )

            CustomTextField(
                value = nimNip,
                onValueChange = { nimNip = it },
                label = "NIM/NIP",
                modifier = Modifier.padding(bottom = 12.dp)
            )

            CustomTextField(
                value = noHp,
                onValueChange = { noHp = it },
                label = "No HP",
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
                    viewModel.updateProfile(namaLengkap, jabatan, nimNip.ifEmpty { null }, noHp, email)
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
    val viewModel = ProfileViewModel(context)
    val passwordState by viewModel.passwordState.collectAsStateWithLifecycle()

    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    LaunchedEffect(passwordState) {
        if (passwordState is Resource.Success) {
            onBack()
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
                value = oldPassword,
                onValueChange = { oldPassword = it },
                label = "Password Lama",
                isPassword = true,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            CustomTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = "Password Baru",
                isPassword = true,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            CustomTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Konfirmasi Password",
                isPassword = true,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            CustomButton(
                text = "Ubah Password",
                onClick = {
                    if (newPassword == confirmPassword) {
                        viewModel.changePassword(oldPassword, newPassword)
                    }
                },
                isLoading = passwordState is Resource.Loading
            )

            if (passwordState is Resource.Error) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = (passwordState as Resource.Error).message,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.error,
                    style = Typography.bodySmall
                )
            }
        }
    }
}
