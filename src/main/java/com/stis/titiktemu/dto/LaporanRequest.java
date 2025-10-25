package com.stis.titiktemu.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// ===================== LAPORAN DTOs =====================

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LaporanRequest {
    @NotBlank(message = "Tipe tidak boleh kosong")
    private String tipe; // HILANG atau TEMUKAN

    @NotBlank(message = "Judul tidak boleh kosong")
    private String judul;

    @NotBlank(message = "Deskripsi tidak boleh kosong")
    private String deskripsi;

    @NotBlank(message = "Kategori tidak boleh kosong")
    private String kategori;

    @NotBlank(message = "Lokasi tidak boleh kosong")
    private String lokasi;

    @NotBlank(message = "Tanggal kejadian tidak boleh kosong")
    private String tanggalKejadian; // Format: YYYY-MM-DD
}
