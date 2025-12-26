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
public class ChangePasswordRequest {
    @NotBlank(message = "Password lama tidak boleh kosong")
    private String oldPassword;

    @NotBlank(message = "Password baru tidak boleh kosong")
    @Size(min = 6, message = "Password baru minimal 6 karakter")
    private String newPassword;
}