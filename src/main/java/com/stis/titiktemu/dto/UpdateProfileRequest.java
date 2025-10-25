package com.stis.titiktemu.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// ===================== USER DTOs =====================

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {
    @NotBlank(message = "Nama lengkap tidak boleh kosong")
    private String namaLengkap;

    @NotBlank(message = "Jabatan tidak boleh kosong")
    private String jabatan;

    private String nimNip;

    @NotBlank(message = "Nomor HP tidak boleh kosong")
    private String noHp;

    @Email(message = "Format email tidak valid")
    private String email;
}
