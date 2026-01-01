package com.stis.titiktemu.data.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.stis.titiktemu.data.api.RetrofitClient
import com.stis.titiktemu.data.local.PreferencesManager
import com.stis.titiktemu.data.model.AuthResponse
import com.stis.titiktemu.data.model.ErrorResponse
import com.stis.titiktemu.data.model.LoginRequest
import com.stis.titiktemu.data.model.RegisterRequest
import com.stis.titiktemu.util.Resource
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

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
        } catch (e: HttpException) {
            Log.e("AuthRepository", "❌ Login HTTP error: ${e.code()}", e)
            val errorMessage = when (e.code()) {
                400 -> "Username atau password tidak boleh kosong"
                401 -> "Username atau password salah"
                404 -> "User tidak ditemukan"
                500 -> "Server error, coba lagi nanti"
                else -> {
                    // Try to parse error response from backend
                    try {
                        val errorBody = e.response()?.errorBody()?.string()
                        if (!errorBody.isNullOrEmpty()) {
                            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                            errorResponse.message ?: "Login gagal"
                        } else {
                            "Login gagal (${e.code()})"
                        }
                    } catch (parseException: Exception) {
                        "Login gagal (${e.code()})"
                    }
                }
            }
            Resource.Error(errorMessage)
        } catch (e: UnknownHostException) {
            Log.e("AuthRepository", "❌ Network error", e)
            Resource.Error("Tidak dapat terhubung ke server. Periksa koneksi internet Anda")
        } catch (e: SocketTimeoutException) {
            Log.e("AuthRepository", "❌ Timeout error", e)
            Resource.Error("Koneksi timeout. Coba lagi")
        } catch (e: Exception) {
            Log.e("AuthRepository", "❌ Login failed: ${e.message}", e)
            Resource.Error("Login gagal: ${e.message}")
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
        } catch (e: HttpException) {
            Log.e("AuthRepository", "❌ Registration HTTP error: ${e.code()}", e)
            val errorMessage = when (e.code()) {
                400 -> {
                    // Try to get specific error message from backend
                    try {
                        val errorBody = e.response()?.errorBody()?.string()
                        if (!errorBody.isNullOrEmpty()) {
                            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                            errorResponse.message ?: "Data tidak valid"
                        } else {
                            "Data tidak valid"
                        }
                    } catch (parseException: Exception) {
                        "Data tidak valid"
                    }
                }
                409 -> "Username atau email sudah digunakan"
                500 -> "Server error, coba lagi nanti"
                else -> "Registrasi gagal (${e.code()})"
            }
            Resource.Error(errorMessage)
        } catch (e: UnknownHostException) {
            Log.e("AuthRepository", "❌ Network error", e)
            Resource.Error("Tidak dapat terhubung ke server. Periksa koneksi internet Anda")
        } catch (e: SocketTimeoutException) {
            Log.e("AuthRepository", "❌ Timeout error", e)
            Resource.Error("Koneksi timeout. Coba lagi")
        } catch (e: Exception) {
            Log.e("AuthRepository", "❌ Registration failed: ${e.message}", e)
            Resource.Error("Registrasi gagal: ${e.message}")
        }
    }

    suspend fun logout() {
        Log.d("AuthRepository", "🔄 Logging out - clearing all preferences")
        preferencesManager.clearAll()
        Log.d("AuthRepository", "✅ All preferences cleared")
    }
    
    suspend fun clearRegistrationToken() {
        Log.d("AuthRepository", "🔄 Clearing registration token - user needs to login")
        preferencesManager.clearAll()
        Log.d("AuthRepository", "✅ Registration token cleared")
    }
}
