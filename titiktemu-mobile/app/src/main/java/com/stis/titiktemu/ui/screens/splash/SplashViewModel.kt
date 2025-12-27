package com.stis.titiktemu.ui.screens.splash

import android.content.Context
import android.util.Log
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
            try {
                delay(2000) // Splash duration
                val token = preferencesManager.getToken()
                if (!token.isNullOrEmpty() && token != "null") {
                    onNavigateToHome()
                } else {
                    onNavigateToLogin()
                }
            } catch (e: Exception) {
                Log.e("SplashViewModel", "Error checking token", e)
                // Default to login on error
                onNavigateToLogin()
            }
        }
    }

    fun navigateToHome(onNavigateToHome: () -> Unit) {
        viewModelScope.launch {
            try {
                delay(2000) // Splash duration
                Log.d("SplashViewModel", "Navigating to Home without login check")
                onNavigateToHome()
            } catch (e: Exception) {
                Log.e("SplashViewModel", "Error navigating to home", e)
                onNavigateToHome()
            }
        }
    }
}
