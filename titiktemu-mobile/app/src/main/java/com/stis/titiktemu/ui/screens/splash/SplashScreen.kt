package com.stis.titiktemu.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stis.titiktemu.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stis.titiktemu.ui.screens.ViewModelFactory
import com.stis.titiktemu.ui.theme.Primary
import com.stis.titiktemu.ui.theme.Surface

@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: SplashViewModel = viewModel(factory = ViewModelFactory(context))

    LaunchedEffect(Unit) {
        viewModel.checkTokenAndNavigate(
            onNavigateToHome = onNavigateToHome,
            onNavigateToLogin = onNavigateToLogin
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Surface),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.titiktemu_splash),
                contentDescription = "Titik Temu Splash",
                modifier = Modifier.size(480.dp)
            )
            CircularProgressIndicator(color = Primary)
        }
    }
}