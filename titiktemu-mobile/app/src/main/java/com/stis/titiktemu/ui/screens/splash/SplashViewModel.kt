package com.stis.titiktemu.ui.screens.splash

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stis.titiktemu.data.local.PreferencesManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(context: Context) : ViewModel() {
    private val preferencesManager = PreferencesManager(context)

    fun checkTokenAndNavigate(
        onNavigateToHome: () -> Unit,
        onNavigateToLogin: () -> Unit
    ) {
        viewModelScope.launch {
            delay(2000) // Splash duration
            val token = preferencesManager.getToken()
            if (!token.isNullOrEmpty() && token != "null") {
                onNavigateToHome()
            } else {
                onNavigateToLogin()
            }
        }
    }
}
