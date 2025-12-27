package com.stis.titiktemu.ui.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stis.titiktemu.ui.theme.Primary
import com.stis.titiktemu.ui.theme.Surface
import com.stis.titiktemu.ui.theme.Typography

@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val context = LocalContext.current
    val viewModel = SplashViewModel(context)

    LaunchedEffect(Unit) {
        viewModel.checkTokenAndNavigate(onNavigateToHome, onNavigateToLogin)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Primary),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "🎯",
                style = TextStyle(fontSize = 64.sp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Titik Temu",
                style = Typography.displayMedium,
                color = Surface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Lost & Found Platform",
                style = Typography.bodySmall,
                color = Surface
            )
            Spacer(modifier = Modifier.height(32.dp))
            CircularProgressIndicator(color = Surface)
        }
    }
}
