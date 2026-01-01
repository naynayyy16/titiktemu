package com.stis.titiktemu.ui.screens.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stis.titiktemu.ui.components.CustomButton
import com.stis.titiktemu.ui.components.CustomTextField
import com.stis.titiktemu.ui.screens.ViewModelFactory
import com.stis.titiktemu.ui.theme.Primary
import com.stis.titiktemu.ui.theme.TextSecondary
import com.stis.titiktemu.ui.theme.Typography
import com.stis.titiktemu.util.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: AuthViewModel = viewModel(factory = ViewModelFactory(context))
    val loginState by viewModel.loginState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(loginState) {
        // Only navigate if login was successful AND returned a non-empty token
        if (loginState is Resource.Success && (loginState as Resource.Success).data.isNotEmpty()) {
            onNavigateToHome()
        }
    }
    
    // Show snackbar for errors
    LaunchedEffect(loginState) {
        if (loginState is Resource.Error) {
            snackbarHostState.showSnackbar(
                message = (loginState as Resource.Error).message
            )
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Login", style = Typography.headlineMedium) }
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
                text = "Selamat Datang",
                style = Typography.displayMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Masuk ke akun Anda untuk melanjutkan",
                style = Typography.bodySmall,
                color = TextSecondary,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            CustomTextField(
                value = username,
                onValueChange = { username = it },
                label = "Username",
                placeholder = "Masukkan username",
                modifier = Modifier.padding(bottom = 16.dp)
            )

            CustomTextField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                placeholder = "Masukkan password",
                isPassword = true,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            CustomButton(
                text = "Login",
                onClick = { viewModel.login(username, password) },
                isLoading = loginState is Resource.Loading
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
            ) {
                Text(
                    text = "Belum punya akun? ",
                    style = Typography.bodySmall,
                    color = TextSecondary
                )
                Text(
                    text = "Daftar di sini",
                    style = Typography.bodySmall,
                    color = Primary,
                    modifier = Modifier.clickable { onNavigateToRegister() }
                )
            }
        }
    }
}