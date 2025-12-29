package com.stis.titiktemu.data.model

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    val token: String,
    val type: String,
    val username: String,
    val email: String,
    val namaLengkap: String,
    val message: String
)

data class LoginRequest(
    val username: String,
    val password: String
)

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val namaLengkap: String,
    val jabatan: String,
    val nimNip: String?,
    val noHp: String
)

data class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String
)

data class UpdateProfileRequest(
    val namaLengkap: String,
    val jabatan: String,
    val nimNip: String?,
    val noHp: String,
    val email: String
)

data class LaporanRequest(
    val tipe: String,
    val judul: String,
    val deskripsi: String,
    val kategori: String,
    val lokasi: String,
    val tanggalKejadian: String
)

data class UpdateLaporanRequest(
    val judul: String?,
    val deskripsi: String?,
    val kategori: String?,
    val lokasi: String?,
    val tanggalKejadian: String?,
    val status: String?
)

data class MessageResponse(
    val message: String
)
