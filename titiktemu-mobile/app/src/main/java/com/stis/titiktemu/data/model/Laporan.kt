package com.stis.titiktemu.data.model

data class Laporan(
    val id: Long,
    val tipe: String,
    val judul: String,
    val deskripsi: String,
    val kategori: String,
    val lokasi: String,
    val tanggalKejadian: String,
    val status: String,
    val fotoUrl: String?,
    val pelaporNama: String,
    val pelaporJabatan: String,
    val pelaporNoHp: String,
    val pelaporEmail: String,
    val createdAt: String,
    val updatedAt: String
)
