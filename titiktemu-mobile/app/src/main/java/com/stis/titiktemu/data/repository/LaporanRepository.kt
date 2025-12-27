package com.stis.titiktemu.data.repository

import android.content.Context
import com.stis.titiktemu.data.api.RetrofitClient
import com.stis.titiktemu.data.model.Laporan
import com.stis.titiktemu.data.model.LaporanRequest
import com.stis.titiktemu.data.model.MessageResponse
import com.stis.titiktemu.data.model.UpdateLaporanRequest
import com.stis.titiktemu.util.Resource

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
        tanggalKejadian: String
    ): Resource<Laporan> {
        return try {
            val request = LaporanRequest(tipe, judul, deskripsi, kategori, lokasi, tanggalKejadian)
            val laporan = laporanApi.createLaporan(request)
            Resource.Success(laporan)
        } catch (e: Exception) {
            Resource.Error("Failed to create laporan: ${e.message}")
        }
    }

    suspend fun updateLaporan(
        id: Long,
        judul: String? = null,
        deskripsi: String? = null,
        kategori: String? = null,
        lokasi: String? = null,
        tanggalKejadian: String? = null,
        status: String? = null
    ): Resource<Laporan> {
        return try {
            val request = UpdateLaporanRequest(judul, deskripsi, kategori, lokasi, tanggalKejadian, status)
            val laporan = laporanApi.updateLaporan(id, request)
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
