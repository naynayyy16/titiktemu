package com.stis.titiktemu.ui.screens.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stis.titiktemu.data.model.User
import com.stis.titiktemu.data.repository.AuthRepository
import com.stis.titiktemu.data.repository.UserRepository
import com.stis.titiktemu.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
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

    fun loadProfile() {
        _profileState.value = Resource.Loading()
        viewModelScope.launch {
            val result = userRepository.getProfile()
            _profileState.value = result
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
            val result = userRepository.updateProfile(namaLengkap, jabatan, nimNip, noHp, email)
            _updateState.value = result
        }
    }

    fun changePassword(oldPassword: String, newPassword: String) {
        _passwordState.value = Resource.Loading()
        viewModelScope.launch {
            val result = userRepository.changePassword(oldPassword, newPassword)
            _passwordState.value = when (result) {
                is Resource.Success -> Resource.Success("Password changed successfully")
                is Resource.Error -> Resource.Error(result.message)
                is Resource.Loading -> Resource.Loading()
                is Resource.Idle -> Resource.Idle()
            }
        }
    }

    fun deleteAccount() {
        _deleteState.value = Resource.Loading()
        viewModelScope.launch {
            val result = userRepository.deleteAccount()
            _deleteState.value = when (result) {
                is Resource.Success -> Resource.Success("Account deleted")
                is Resource.Error -> Resource.Error(result.message)
                is Resource.Loading -> Resource.Loading()
                is Resource.Idle -> Resource.Idle()
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }
}
