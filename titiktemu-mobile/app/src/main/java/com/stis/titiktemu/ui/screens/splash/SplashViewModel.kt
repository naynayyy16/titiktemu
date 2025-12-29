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
                Log.d("SplashViewModel", "Token check - Token value: '$token'")
                Log.d("SplashViewModel", "Token is null: ${token == null}")
                Log.d("SplashViewModel", "Token is empty: ${token?.isEmpty()}")
                Log.d("SplashViewModel", "Token is 'null' string: ${token == "null"}")
                
                // Valid token must exist, not be null, not be "null" string, and not be empty
                if (!token.isNullOrEmpty() && token != "null") {
                    Log.d("SplashViewModel", "✅ Valid token found - Navigating to Home")
                    onNavigateToHome()
                } else {
                    Log.d("SplashViewModel", "❌ No valid token - Navigating to Login")
                    onNavigateToLogin()
                }
            } catch (e: Exception) {
                Log.e("SplashViewModel", "Error checking token", e)
                // Default to login on error
                onNavigateToLogin()
            }
        }
    }
}
