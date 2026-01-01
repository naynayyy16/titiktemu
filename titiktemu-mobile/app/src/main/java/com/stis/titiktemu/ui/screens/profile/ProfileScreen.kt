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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stis.titiktemu.ui.components.LoadingDialog
import com.stis.titiktemu.ui.screens.ViewModelFactory
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
    val viewModel: ProfileViewModel = viewModel(factory = ViewModelFactory(context))
    val profileState by viewModel.profileState.collectAsStateWithLifecycle()
    val deleteState by viewModel.deleteState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    var showLogoutDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    
    // Listen for logout event
    LaunchedEffect(Unit) {
        viewModel.navigateToLogin.collect {
            onLogout() // Navigate to login when session expires
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }
    
    // Auto logout after successful account deletion
    LaunchedEffect(deleteState) {
        if (deleteState is Resource.Success) {
            snackbarHostState.showSnackbar("Akun berhasil dihapus")
            viewModel.logout()
            onLogout()
        } else if (deleteState is Resource.Error) {
            snackbarHostState.showSnackbar(
                (deleteState as Resource.Error).message
            )
        }
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
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        when (profileState) {
            is Resource.Idle -> {
                // Initial state - no action needed
            }
            is Resource.Loading -> LoadingDialog()
            is Resource.Success -> {
                val user = (profileState as Resource.Success).data
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                        .background(Color(0xFFF5F5F5)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Avatar with shadow and border
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .shadow(8.dp, CircleShape)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Primary, Color(0xFF0288D1))
                                ),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = user.namaLengkap.take(1).uppercase(),
                            style = Typography.displayMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Name and Position
                    Text(
                        user.namaLengkap,
                        style = Typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        user.jabatan,
                        style = Typography.bodyMedium,
                        color = Primary
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    // Info Card with Icons
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .shadow(4.dp, RoundedCornerShape(12.dp))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            ProfileInfoRow(
                                icon = Icons.Default.Person,
                                label = "Username",
                                value = user.username
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            ProfileInfoRow(
                                icon = Icons.Default.Email,
                                label = "Email",
                                value = user.email
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            ProfileInfoRow(
                                icon = Icons.Default.Phone,
                                label = "No HP",
                                value = user.noHp
                            )
                            if (user.nimNip != null) {
                                Spacer(modifier = Modifier.height(12.dp))
                                ProfileInfoRow(
                                    icon = Icons.Default.Person,
                                    label = "NIM/NIP",
                                    value = user.nimNip
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // Menu Items with Icons
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        MenuItem(
                            icon = Icons.Default.Edit,
                            text = "Edit Profil",
                            onClick = { onEditProfile() }
                        )
                        MenuItem(
                            icon = Icons.Default.Lock,
                            text = "Ganti Password",
                            onClick = { onChangePassword() }
                        )
                        MenuItem(
                            icon = Icons.Default.Logout,
                            text = "Keluar",
                            danger = true,
                            onClick = { showLogoutDialog = true }
                        )
                        MenuItem(
                            icon = Icons.Default.Delete,
                            text = "Hapus Akun",
                            danger = true,
                            onClick = { showDeleteDialog = true }
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
            is Resource.Error -> {
                val errorMessage = (profileState as Resource.Error).message
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = errorMessage,
                        style = Typography.bodyLarge,
                        color = Color.Red
                    )
                }
                
                // Show snackbar for error
                LaunchedEffect(errorMessage) {
                    snackbarHostState.showSnackbar(errorMessage)
                }
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
fun ProfileInfoRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.padding(horizontal = 12.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(label, style = Typography.labelSmall, color = TextSecondary)
            Text(value, style = Typography.bodyMedium, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun MenuItem(
    icon: ImageVector,
    text: String,
    danger: Boolean = false,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(3.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(if (danger) Color(0xFFFFEBEE) else Color.White)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = if (danger) Color(0xFFD32F2F) else Primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.padding(horizontal = 16.dp))
            Text(
                text = text,
                style = Typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                color = if (danger) Color(0xFFD32F2F) else Color.Black,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = if (danger) Color(0xFFD32F2F) else Primary,
                modifier = Modifier
                    .size(20.dp)
                    .rotate(180f)
            )
        }
    }
}