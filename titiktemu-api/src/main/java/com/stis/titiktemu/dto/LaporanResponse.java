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
public class LaporanResponse {
    private Long id;
    private String tipe;
    private String judul;
    private String deskripsi;
    private String kategori;
    private String lokasi;
    private String tanggalKejadian;
    private String status;
    private String fotoUrl;
    private String pelaporNama;
    private String pelaporJabatan;
    private String pelaporNoHp;
    private String pelaporEmail;
    private String createdAt;
    private String updatedAt;
}