package com.stis.titiktemu.data.api

import com.stis.titiktemu.data.model.*
import retrofit2.http.*

interface AuthApi {
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse
}

interface UserApi {
    @GET("users/profile")
    suspend fun getProfile(): User

    @PUT("users/profile")
    suspend fun updateProfile(@Body request: UpdateProfileRequest): User

    @PUT("users/change-password")
    suspend fun changePassword(@Body request: ChangePasswordRequest): MessageResponse

    @DELETE("users/account")
    suspend fun deleteAccount(): MessageResponse
}

interface LaporanApi {
    @GET("laporan")
    suspend fun getLaporan(
        @Query("tipe") tipe: String? = null,
        @Query("kategori") kategori: String? = null,
        @Query("status") status: String? = null,
        @Query("lokasi") lokasi: String? = null,
        @Query("search") search: String? = null
    ): List<Laporan>

    @GET("laporan/{id}")
    suspend fun getLaporanDetail(@Path("id") id: Long): Laporan

    @POST("laporan")
    suspend fun createLaporan(@Body request: LaporanRequest): Laporan

    @PUT("laporan/{id}")
    suspend fun updateLaporan(
        @Path("id") id: Long,
        @Body request: UpdateLaporanRequest
    ): Laporan

    @DELETE("laporan/{id}")
    suspend fun deleteLaporan(@Path("id") id: Long): MessageResponse
}
