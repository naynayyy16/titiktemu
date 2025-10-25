package com.stis.titiktemu.controller;

import com.stis.titiktemu.dto.ChangePasswordRequest;
import com.stis.titiktemu.dto.MessageResponse;
import com.stis.titiktemu.dto.UpdateProfileRequest;
import com.stis.titiktemu.dto.UserResponse;
import com.stis.titiktemu.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "User Management", description = "Endpoint untuk manajemen profil user (memerlukan authentication)")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    @Operation(
            summary = "Lihat profil user yang sedang login",
            description = "Menampilkan informasi lengkap profil user berdasarkan token JWT yang digunakan"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Data profil user berhasil diambil",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token tidak valid atau sudah expired",
                    content = @Content
            )
    })
    public ResponseEntity<UserResponse> getProfile() {
        UserResponse response = userService.getProfile();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/profile")
    @Operation(
            summary = "Update profil user",
            description = "Mengubah informasi profil user. Hanya user yang sedang login yang bisa mengubah profilnya sendiri."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Profil berhasil diupdate",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validasi gagal atau email sudah digunakan",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token tidak valid",
                    content = @Content
            )
    })
    public ResponseEntity<?> updateProfile(@Valid @RequestBody UpdateProfileRequest request) {
        try {
            UserResponse response = userService.updateProfile(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PutMapping("/change-password")
    @Operation(
            summary = "Ganti password",
            description = "Mengubah password user. Memerlukan password lama untuk verifikasi."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Password berhasil diubah",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Password lama tidak sesuai atau validasi gagal",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token tidak valid",
                    content = @Content
            )
    })
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        try {
            userService.changePassword(request);
            return ResponseEntity.ok(new MessageResponse("Password berhasil diubah"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/account")
    @Operation(
            summary = "Hapus akun",
            description = "Menghapus akun user yang sedang login beserta semua laporan yang dibuat (cascade delete)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Akun berhasil dihapus",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token tidak valid",
                    content = @Content
            )
    })
    public ResponseEntity<?> deleteAccount() {
        try {
            userService.deleteAccount();
            return ResponseEntity.ok(new MessageResponse("Akun berhasil dihapus"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}