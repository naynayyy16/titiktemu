package com.stis.titiktemu.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stis.titiktemu.ui.components.LoadingDialog
import com.stis.titiktemu.ui.theme.Primary
import com.stis.titiktemu.ui.theme.TextSecondary
import com.stis.titiktemu.ui.theme.Typography
import com.stis.titiktemu.util.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    onEditProfile: () -> Unit,
    onChangePassword: () -> Unit,
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    val viewModel = ProfileViewModel(context)
    val profileState by viewModel.profileState.collectAsStateWithLifecycle()

    var showLogoutDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Profil", style = Typography.headlineMedium) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        when (profileState) {
            is Resource.Loading -> LoadingDialog()
            is Resource.Success -> {
                val user = (profileState as Resource.Success).data
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .padding(bottom = 16.dp)
                            .background(
                                color = Primary,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = user.namaLengkap.take(1).uppercase(),
                            style = Typography.displayMedium,
                            color = Color.White
                        )
                    }

                    // Name
                    Text(user.namaLengkap, style = Typography.headlineMedium)
                    Text(user.jabatan, style = Typography.bodySmall, color = TextSecondary)

                    Spacer(modifier = Modifier.height(24.dp))

                    // Info Card
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            ProfileInfoRow("Username", user.username)
                            ProfileInfoRow("Email", user.email)
                            ProfileInfoRow("No HP", user.noHp)
                            if (user.nimNip != null) {
                                ProfileInfoRow("NIM/NIP", user.nimNip)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Menu Items
                    MenuItem("✏️ Edit Profil") { onEditProfile() }
                    MenuItem("🔐 Ganti Password") { onChangePassword() }
                    MenuItem("🚪 Keluar", danger = true) { showLogoutDialog = true }
                    MenuItem("🗑️ Hapus Akun", danger = true) { showDeleteDialog = true }
                }
            }
            is Resource.Error -> {
                Text((profileState as Resource.Error).message)
            }
        }
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Keluar?") },
            text = { Text("Apakah Anda yakin ingin keluar dari aplikasi?") },
            confirmButton = {
                Button(onClick = {
                    viewModel.logout()
                    onLogout()
                }) {
                    Text("Keluar")
                }
            },
            dismissButton = {
                Button(onClick = { showLogoutDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Hapus Akun?") },
            text = { Text("Tindakan ini tidak dapat dibatalkan. Semua data Anda akan dihapus.") },
            confirmButton = {
                Button(onClick = {
                    viewModel.deleteAccount()
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
}

@Composable
fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(label, style = Typography.labelMedium, modifier = Modifier.weight(0.3f))
        Text(value, style = Typography.bodySmall, modifier = Modifier.weight(0.7f))
    }
}

@Composable
fun MenuItem(text: String, danger: Boolean = false, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Text(
            text = text,
            style = Typography.bodyMedium,
            color = if (danger) Color.Red else Color.Black,
            modifier = Modifier.padding(16.dp)
        )
    }
}
