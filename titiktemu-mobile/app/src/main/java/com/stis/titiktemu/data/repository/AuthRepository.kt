package com.stis.titiktemu.data.repository

import android.content.Context
import android.util.Log
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
            Log.d("AuthRepository", "🔄 Attempting login for user: $username")
            val response = authApi.login(request)
            Log.d("AuthRepository", "✅ Login successful! Token: ${response.token}")
            preferencesManager.saveToken(response.token)
            Log.d("AuthRepository", "✅ Token saved to preferences")
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
            Log.e("AuthRepository", "❌ Login failed: ${e.message}", e)
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
            Log.d("AuthRepository", "🔄 Attempting registration for user: $username")
            val response = authApi.register(request)
            Log.d("AuthRepository", "✅ Registration successful! Token: ${response.token}")
            preferencesManager.saveToken(response.token)
            Log.d("AuthRepository", "✅ Token saved to preferences")
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
            Log.e("AuthRepository", "❌ Registration failed: ${e.message}", e)
            Resource.Error("Registration failed: ${e.message}")
        }
    }

    suspend fun logout() {
        Log.d("AuthRepository", "🔄 Logging out - clearing all preferences")
        preferencesManager.clearAll()
        Log.d("AuthRepository", "✅ All preferences cleared")
    }
}
