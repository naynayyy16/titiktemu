package com.stis.titiktemu.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// =====================================================

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLaporanRequest {
    private String judul;
    private String deskripsi;
    private String kategori;
    private String lokasi;
    private String tanggalKejadian;
    private String status; // AKTIF atau SELESAI
}