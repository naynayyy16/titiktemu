package com.stis.titiktemu.ui.screens.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stis.titiktemu.data.repository.AuthRepository
import com.stis.titiktemu.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(context: Context) : ViewModel() {
    private val authRepository = AuthRepository(context)

    // Login state - Start with Idle state, not Loading
    private val _loginState = MutableStateFlow<Resource<String>>(Resource.Idle())
    val loginState = _loginState.asStateFlow()

    fun login(username: String, password: String) {
        _loginState.value = Resource.Loading()
        viewModelScope.launch {
            val result = authRepository.login(username, password)
            _loginState.value = when (result) {
                is Resource.Success -> Resource.Success(result.data.token)
                is Resource.Error -> Resource.Error(result.message)
                is Resource.Loading -> Resource.Loading()
                is Resource.Idle -> Resource.Idle()
            }
        }
    }

    fun register(
        username: String,
        email: String,
        password: String,
        namaLengkap: String,
        jabatan: String,
        nimNip: String?,
        noHp: String
    ) {
        _loginState.value = Resource.Loading()
        viewModelScope.launch {
            val result = authRepository.register(
                username, email, password, namaLengkap, jabatan, nimNip, noHp
            )
            _loginState.value = when (result) {
                is Resource.Success -> Resource.Success(result.data.token)
                is Resource.Error -> Resource.Error(result.message)
                is Resource.Loading -> Resource.Loading()
                is Resource.Idle -> Resource.Idle()
            }
        }
    }
}
