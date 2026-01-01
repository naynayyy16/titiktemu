package com.stis.titiktemu.data.repository

import android.content.Context
import com.google.gson.Gson
import com.stis.titiktemu.data.api.RetrofitClient
import com.stis.titiktemu.data.local.PreferencesManager
import com.stis.titiktemu.data.model.ChangePasswordRequest
import com.stis.titiktemu.data.model.ErrorResponse
import com.stis.titiktemu.data.model.MessageResponse
import com.stis.titiktemu.data.model.UpdateProfileRequest
import com.stis.titiktemu.data.model.User
import com.stis.titiktemu.util.Resource
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class UserRepository(private val context: Context) {
    private val userApi = RetrofitClient.getUserApi(context)
    private val preferencesManager = PreferencesManager(context)

    suspend fun getProfile(): Resource<User> {
        return try {
            val user = userApi.getProfile()
            Resource.Success(user)
        } catch (e: Exception) {
            Resource.Error("Failed to fetch profile: ${e.message}")
        }
    }

    suspend fun updateProfile(
        namaLengkap: String,
        jabatan: String,
        nimNip: String?,
        noHp: String,
        email: String
    ): Resource<User> {
        return try {
            val request = UpdateProfileRequest(namaLengkap, jabatan, nimNip, noHp, email)
            val user = userApi.updateProfile(request)
            preferencesManager.saveUserData(
                username = "", // Keep existing
                email = user.email,
                namaLengkap = user.namaLengkap,
                jabatan = user.jabatan,
                nimNip = user.nimNip,
                noHp = user.noHp
            )
            Resource.Success(user)
        } catch (e: Exception) {
            Resource.Error("Failed to update profile: ${e.message}")
        }
    }

    suspend fun changePassword(oldPassword: String, newPassword: String): Resource<MessageResponse> {
        return try {
            val request = ChangePasswordRequest(oldPassword, newPassword)
            val response = userApi.changePassword(request)
            Resource.Success(response)
        } catch (e: HttpException) {
            val errorMessage = when (e.code()) {
                400 -> "Data tidak valid"
                401 -> "Password lama salah"
                404 -> "User tidak ditemukan"
                500 -> "Server error, coba lagi nanti"
                else -> {
                    try {
                        val errorBody = e.response()?.errorBody()?.string()
                        if (!errorBody.isNullOrEmpty()) {
                            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                            errorResponse.message ?: "Gagal mengubah password"
                        } else {
                            "Gagal mengubah password"
                        }
                    } catch (parseException: Exception) {
                        "Gagal mengubah password"
                    }
                }
            }
            Resource.Error(errorMessage)
        } catch (e: UnknownHostException) {
            Resource.Error("Tidak dapat terhubung ke server. Periksa koneksi internet Anda")
        } catch (e: SocketTimeoutException) {
            Resource.Error("Koneksi timeout. Coba lagi")
        } catch (e: Exception) {
            Resource.Error("Gagal mengubah password: ${e.message}")
        }
    }

    suspend fun deleteAccount(): Resource<MessageResponse> {
        return try {
            val response = userApi.deleteAccount()
            preferencesManager.clearAll()
            Resource.Success(response)
        } catch (e: HttpException) {
            val errorMessage = when (e.code()) {
                400 -> "Permintaan tidak valid"
                401 -> "Tidak dapat memverifikasi akun Anda"
                403 -> "Akses ditolak"
                404 -> "Akun tidak ditemukan"
                500 -> "Server error, coba lagi nanti"
                else -> {
                    try {
                        val errorBody = e.response()?.errorBody()?.string()
                        if (!errorBody.isNullOrEmpty()) {
                            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                            errorResponse.message ?: "Gagal menghapus akun"
                        } else {
                            "Gagal menghapus akun"
                        }
                    } catch (parseException: Exception) {
                        "Gagal menghapus akun"
                    }
                }
            }
            Resource.Error(errorMessage)
        } catch (e: UnknownHostException) {
            Resource.Error("Tidak dapat terhubung ke server. Periksa koneksi internet Anda")
        } catch (e: SocketTimeoutException) {
            Resource.Error("Koneksi timeout. Coba lagi")
        } catch (e: Exception) {
            Resource.Error("Gagal menghapus akun: ${e.message}")
        }
    }
}
