package com.stis.titiktemu.ui.screens.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stis.titiktemu.data.api.UnauthorizedException
import com.stis.titiktemu.data.model.User
import com.stis.titiktemu.data.repository.AuthRepository
import com.stis.titiktemu.data.repository.UserRepository
import com.stis.titiktemu.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(context: Context) : ViewModel() {
    private val userRepository = UserRepository(context)
    private val authRepository = AuthRepository(context)

    private val _profileState = MutableStateFlow<Resource<User>>(Resource.Loading())
    val profileState = _profileState.asStateFlow()

    private val _updateState = MutableStateFlow<Resource<User>>(Resource.Idle())
    val updateState = _updateState.asStateFlow()

    private val _passwordState = MutableStateFlow<Resource<String>>(Resource.Idle())
    val passwordState = _passwordState.asStateFlow()

    private val _deleteState = MutableStateFlow<Resource<String>>(Resource.Idle())
    val deleteState = _deleteState.asStateFlow()
    
    private val _navigateToLogin = MutableSharedFlow<Unit>()
    val navigateToLogin = _navigateToLogin.asSharedFlow()

    fun loadProfile() {
        _profileState.value = Resource.Loading()
        viewModelScope.launch {
            try {
                val result = userRepository.getProfile()
                _profileState.value = result
            } catch (e: UnauthorizedException) {
                _profileState.value = Resource.Error("Session telah berakhir. Silakan login kembali.")
                _navigateToLogin.emit(Unit)
            } catch (e: Exception) {
                _profileState.value = Resource.Error(e.message ?: "Gagal memuat profil")
            }
        }
    }

    fun updateProfile(
        namaLengkap: String,
        jabatan: String,
        nimNip: String?,
        noHp: String,
        email: String
    ) {
        _updateState.value = Resource.Loading()
        viewModelScope.launch {
            try {
                val result = userRepository.updateProfile(namaLengkap, jabatan, nimNip, noHp, email)
                _updateState.value = result
            } catch (e: UnauthorizedException) {
                _updateState.value = Resource.Error("Session expired")
                _navigateToLogin.emit(Unit)
            } catch (e: Exception) {
                _updateState.value = Resource.Error(e.message ?: "Error")
            }
        }
    }

    fun changePassword(oldPassword: String, newPassword: String) {
        _passwordState.value = Resource.Loading()
        viewModelScope.launch {
            try {
                val result = userRepository.changePassword(oldPassword, newPassword)
                _passwordState.value = when (result) {
                    is Resource.Success -> Resource.Success("Password changed successfully")
                    is Resource.Error -> Resource.Error(result.message)
                    is Resource.Loading -> Resource.Loading()
                    is Resource.Idle -> Resource.Idle()
                }
            } catch (e: UnauthorizedException) {
                _passwordState.value = Resource.Error("Session expired")
                _navigateToLogin.emit(Unit)
            } catch (e: Exception) {
                _passwordState.value = Resource.Error(e.message ?: "Error")
            }
        }
    }

    fun deleteAccount() {
        _deleteState.value = Resource.Loading()
        viewModelScope.launch {
            try {
                val result = userRepository.deleteAccount()
                _deleteState.value = when (result) {
                    is Resource.Success -> Resource.Success("Account deleted")
                    is Resource.Error -> Resource.Error(result.message)
                    is Resource.Loading -> Resource.Loading()
                    is Resource.Idle -> Resource.Idle()
                }
            } catch (e: UnauthorizedException) {
                _deleteState.value = Resource.Error("Session expired")
                _navigateToLogin.emit(Unit)
            } catch (e: Exception) {
                _deleteState.value = Resource.Error(e.message ?: "Error")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }
}
