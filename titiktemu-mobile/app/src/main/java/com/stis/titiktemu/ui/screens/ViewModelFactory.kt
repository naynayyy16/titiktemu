package com.stis.titiktemu.ui.screens

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(com.stis.titiktemu.ui.screens.auth.AuthViewModel::class.java) -> {
                com.stis.titiktemu.ui.screens.auth.AuthViewModel(context) as T
            }
            modelClass.isAssignableFrom(com.stis.titiktemu.ui.screens.home.HomeViewModel::class.java) -> {
                com.stis.titiktemu.ui.screens.home.HomeViewModel(context) as T
            }
            modelClass.isAssignableFrom(com.stis.titiktemu.ui.screens.detail.DetailViewModel::class.java) -> {
                com.stis.titiktemu.ui.screens.detail.DetailViewModel(context) as T
            }
            modelClass.isAssignableFrom(com.stis.titiktemu.ui.screens.create.CreateViewModel::class.java) -> {
                com.stis.titiktemu.ui.screens.create.CreateViewModel(context) as T
            }
            modelClass.isAssignableFrom(com.stis.titiktemu.ui.screens.edit.EditViewModel::class.java) -> {
                com.stis.titiktemu.ui.screens.edit.EditViewModel(context) as T
            }
            modelClass.isAssignableFrom(com.stis.titiktemu.ui.screens.profile.ProfileViewModel::class.java) -> {
                com.stis.titiktemu.ui.screens.profile.ProfileViewModel(context) as T
            }
            modelClass.isAssignableFrom(com.stis.titiktemu.ui.screens.splash.SplashViewModel::class.java) -> {
                com.stis.titiktemu.ui.screens.splash.SplashViewModel(context) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}