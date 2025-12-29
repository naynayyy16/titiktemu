package com.stis.titiktemu.data.repository

import android.content.Context
import com.stis.titiktemu.data.api.RetrofitClient
import com.stis.titiktemu.data.local.PreferencesManager
import com.stis.titiktemu.data.model.ChangePasswordRequest
import com.stis.titiktemu.data.model.MessageResponse
import com.stis.titiktemu.data.model.UpdateProfileRequest
import com.stis.titiktemu.data.model.User
import com.stis.titiktemu.util.Resource

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
        } catch (e: Exception) {
            Resource.Error("Failed to change password: ${e.message}")
        }
    }

    suspend fun deleteAccount(): Resource<MessageResponse> {
        return try {
            val response = userApi.deleteAccount()
            preferencesManager.clearAll()
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error("Failed to delete account: ${e.message}")
        }
    }
}
