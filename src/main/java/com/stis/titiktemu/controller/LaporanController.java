package com.stis.titiktemu.controller;

import com.stis.titiktemu.dto.LaporanRequest;
import com.stis.titiktemu.dto.LaporanResponse;
import com.stis.titiktemu.dto.MessageResponse;
import com.stis.titiktemu.dto.UpdateLaporanRequest;
import com.stis.titiktemu.service.LaporanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/laporan")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Laporan Lost & Found", description = "Endpoint untuk manajemen laporan barang hilang dan ditemukan (memerlukan authentication)")
public class LaporanController {

    @Autowired
    private LaporanService laporanService;

    @PostMapping
    @Operation(
            summary = "Buat laporan baru",
            description = "Membuat laporan barang hilang atau ditemukan. Tipe laporan: HILANG atau TEMUKAN. " +
                    "Kategori: ELEKTRONIK, ALAT_TULIS, AKSESORIS_PRIBADI, ALAT_MAKAN, DOKUMEN, ATRIBUT_KAMPUS, LAINNYA. " +
                    "Format tanggal: YYYY-MM-DD (contoh: 2025-10-25)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Laporan berhasil dibuat",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LaporanResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validasi gagal atau tipe/kategori tidak valid",
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
    public ResponseEntity<?> createLaporan(@Valid @RequestBody LaporanRequest request) {
        try {
            LaporanResponse response = laporanService.createLaporan(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping
    @Operation(
            summary = "List semua laporan dengan filter",
            description = "Menampilkan daftar laporan dengan berbagai filter optional: " +
                    "tipe (HILANG/TEMUKAN), kategori, status (AKTIF/SELESAI), lokasi, dan search keyword. " +
                    "Filter dapat dikombinasikan. Jika tidak ada filter, menampilkan semua laporan diurutkan dari terbaru."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Daftar laporan berhasil diambil (dapat berupa array kosong jika tidak ada data)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LaporanResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token tidak valid",
                    content = @Content
            )
    })
    public ResponseEntity<List<LaporanResponse>> getAllLaporan(
            @Parameter(description = "Filter berdasarkan tipe: HILANG atau TEMUKAN", example = "HILANG")
            @RequestParam(required = false) String tipe,

            @Parameter(description = "Filter berdasarkan kategori: ELEKTRONIK, ALAT_TULIS, AKSESORIS_PRIBADI, ALAT_MAKAN, DOKUMEN, ATRIBUT_KAMPUS, LAINNYA", example = "ELEKTRONIK")
            @RequestParam(required = false) String kategori,

            @Parameter(description = "Filter berdasarkan status: AKTIF atau SELESAI", example = "AKTIF")
            @RequestParam(required = false) String status,

            @Parameter(description = "Filter berdasarkan lokasi (partial match, case insensitive)", example = "kantin")
            @RequestParam(required = false) String lokasi,

            @Parameter(description = "Cari berdasarkan kata kunci di judul atau deskripsi (case insensitive)", example = "iphone")
            @RequestParam(required = false) String search
    ) {
        List<LaporanResponse> response = laporanService.getAllLaporan(tipe, kategori, status, lokasi, search);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Detail laporan berdasarkan ID",
            description = "Menampilkan detail lengkap laporan termasuk informasi kontak pelapor"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Detail laporan berhasil diambil",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LaporanResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Laporan tidak ditemukan",
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
    public ResponseEntity<?> getLaporanById(
            @Parameter(description = "ID laporan yang ingin diambil", example = "1")
            @PathVariable Long id
    ) {
        try {
            LaporanResponse response = laporanService.getLaporanById(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update laporan (hanya pemilik laporan)",
            description = "Mengubah informasi laporan. Hanya user yang membuat laporan tersebut yang bisa mengupdate. " +
                    "Semua field bersifat optional, hanya field yang diisi yang akan diupdate."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Laporan berhasil diupdate",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LaporanResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validasi gagal atau user bukan pemilik laporan",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token tidak valid",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Laporan tidak ditemukan",
                    content = @Content
            )
    })
    public ResponseEntity<?> updateLaporan(
            @Parameter(description = "ID laporan yang ingin diupdate", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody UpdateLaporanRequest request
    ) {
        try {
            LaporanResponse response = laporanService.updateLaporan(id, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Hapus laporan (hanya pemilik laporan)",
            description = "Menghapus laporan. Hanya user yang membuat laporan tersebut yang bisa menghapus."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Laporan berhasil dihapus",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "User bukan pemilik laporan",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token tidak valid",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Laporan tidak ditemukan",
                    content = @Content
            )
    })
    public ResponseEntity<?> deleteLaporan(
            @Parameter(description = "ID laporan yang ingin dihapus", example = "1")
            @PathVariable Long id
    ) {
        try {
            laporanService.deleteLaporan(id);
            return ResponseEntity.ok(new MessageResponse("Laporan berhasil dihapus"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}