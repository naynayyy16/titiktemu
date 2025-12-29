package com.stis.titiktemu.data.repository

import android.content.Context
import android.net.Uri
import com.stis.titiktemu.data.api.RetrofitClient
import com.stis.titiktemu.data.model.Laporan
import com.stis.titiktemu.data.model.LaporanRequest
import com.stis.titiktemu.data.model.MessageResponse
import com.stis.titiktemu.data.model.UpdateLaporanRequest
import com.stis.titiktemu.util.Resource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class LaporanRepository(private val context: Context) {
    private val laporanApi = RetrofitClient.getLaporanApi(context)

    suspend fun getLaporan(
        tipe: String? = null,
        kategori: String? = null,
        status: String? = null,
        lokasi: String? = null,
        search: String? = null
    ): Resource<List<Laporan>> {
        return try {
            val laporan = laporanApi.getLaporan(tipe, kategori, status, lokasi, search)
            Resource.Success(laporan)
        } catch (e: Exception) {
            Resource.Error("Failed to fetch laporan: ${e.message}")
        }
    }

    suspend fun getLaporanDetail(id: Long): Resource<Laporan> {
        return try {
            val laporan = laporanApi.getLaporanDetail(id)
            Resource.Success(laporan)
        } catch (e: Exception) {
            Resource.Error("Failed to fetch detail: ${e.message}")
        }
    }

    suspend fun createLaporan(
        tipe: String,
        judul: String,
        deskripsi: String,
        kategori: String,
        lokasi: String,
        tanggalKejadian: String,
        imageUri: Uri? = null
    ): Resource<Laporan> {
        return try {
            val tipeBody = tipe.toRequestBody("text/plain".toMediaTypeOrNull())
            val judulBody = judul.toRequestBody("text/plain".toMediaTypeOrNull())
            val deskripsiBody = deskripsi.toRequestBody("text/plain".toMediaTypeOrNull())
            val kategoriBody = kategori.toRequestBody("text/plain".toMediaTypeOrNull())
            val lokasiBody = lokasi.toRequestBody("text/plain".toMediaTypeOrNull())
            val tanggalBody = tanggalKejadian.toRequestBody("text/plain".toMediaTypeOrNull())
            
            val fotoPart = imageUri?.let { uri ->
                val inputStream = context.contentResolver.openInputStream(uri)
                val file = File(context.cacheDir, "upload_${System.currentTimeMillis()}.jpg")
                inputStream?.use { input ->
                    file.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("foto", file.name, requestFile)
            }
            
            val laporan = laporanApi.createLaporan(
                tipeBody, judulBody, deskripsiBody, kategoriBody, lokasiBody, tanggalBody, fotoPart
            )
            Resource.Success(laporan)
        } catch (e: Exception) {
            Resource.Error("Failed to create laporan: ${e.message}")
        }
    }

    suspend fun updateLaporan(
        id: Long,
        judul: String,
        deskripsi: String,
        kategori: String,
        lokasi: String,
        tanggalKejadian: String,
        status: String,
        imageUri: Uri? = null
    ): Resource<Laporan> {
        return try {
            val judulBody = judul.toRequestBody("text/plain".toMediaTypeOrNull())
            val deskripsiBody = deskripsi.toRequestBody("text/plain".toMediaTypeOrNull())
            val kategoriBody = kategori.toRequestBody("text/plain".toMediaTypeOrNull())
            val lokasiBody = lokasi.toRequestBody("text/plain".toMediaTypeOrNull())
            val tanggalBody = tanggalKejadian.toRequestBody("text/plain".toMediaTypeOrNull())
            val statusBody = status.toRequestBody("text/plain".toMediaTypeOrNull())
            
            val fotoPart = imageUri?.let { uri ->
                val inputStream = context.contentResolver.openInputStream(uri)
                val file = File(context.cacheDir, "upload_${System.currentTimeMillis()}.jpg")
                inputStream?.use { input ->
                    file.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("foto", file.name, requestFile)
            }
            
            val laporan = laporanApi.updateLaporan(
                id, judulBody, deskripsiBody, kategoriBody, lokasiBody, tanggalBody, statusBody, fotoPart
            )
            Resource.Success(laporan)
        } catch (e: Exception) {
            Resource.Error("Failed to update laporan: ${e.message}")
        }
    }

    suspend fun deleteLaporan(id: Long): Resource<MessageResponse> {
        return try {
            val response = laporanApi.deleteLaporan(id)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error("Failed to delete laporan: ${e.message}")
        }
    }
}
