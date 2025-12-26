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
public class AuthResponse {
    private String token;
    private String type = "Bearer";
    private String username;
    private String email;
    private String namaLengkap;
    private String message;

    public AuthResponse(String token, String username, String email, String namaLengkap) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.namaLengkap = namaLengkap;
        this.message = "Login berhasil";
    }
}