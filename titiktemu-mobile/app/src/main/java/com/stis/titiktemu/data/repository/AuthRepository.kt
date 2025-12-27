package com.stis.titiktemu.data.repository

import android.content.Context
import com.stis.titiktemu.data.api.RetrofitClient
import com.stis.titiktemu.data.local.PreferencesManager
import com.stis.titiktemu.data.model.AuthResponse
import com.stis.titiktemu.data.model.LoginRequest
import com.stis.titiktemu.data.model.RegisterRequest
import com.stis.titiktemu.util.Resource

class AuthRepository(private val context: Context) {
    private val authApi = RetrofitClient.getAuthApi(context)
    private val preferencesManager = PreferencesManager(context)

    suspend fun login(username: String, password: String): Resource<AuthResponse> {
        return try {
            val request = LoginRequest(username, password)
            val response = authApi.login(request)
            preferencesManager.saveToken(response.token)
            preferencesManager.saveUserData(
                username = response.username,
                email = response.email,
                namaLengkap = response.namaLengkap,
                jabatan = "",
                nimNip = null,
                noHp = ""
            )
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error("Login failed: ${e.message}")
        }
    }

    suspend fun register(
        username: String,
        email: String,
        password: String,
        namaLengkap: String,
        jabatan: String,
        nimNip: String?,
        noHp: String
    ): Resource<AuthResponse> {
        return try {
            val request = RegisterRequest(
                username = username,
                email = email,
                password = password,
                namaLengkap = namaLengkap,
                jabatan = jabatan,
                nimNip = nimNip,
                noHp = noHp
            )
            val response = authApi.register(request)
            preferencesManager.saveToken(response.token)
            preferencesManager.saveUserData(
                username = response.username,
                email = response.email,
                namaLengkap = response.namaLengkap,
                jabatan = jabatan,
                nimNip = nimNip,
                noHp = noHp
            )
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error("Registration failed: ${e.message}")
        }
    }

    suspend fun logout() {
        preferencesManager.clearAll()
    }
}
