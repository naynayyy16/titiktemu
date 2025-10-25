package com.stis.titiktemu.controller;

import com.stis.titiktemu.dto.LaporanRequest;
import com.stis.titiktemu.dto.LaporanResponse;
import com.stis.titiktemu.dto.MessageResponse;
import com.stis.titiktemu.dto.UpdateLaporanRequest;
import com.stis.titiktemu.service.LaporanService;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "Laporan Lost & Found", description = "Endpoint untuk manajemen laporan barang hilang dan ditemukan")
public class LaporanController {

    @Autowired
    private LaporanService laporanService;

    @PostMapping
    @Operation(summary = "Buat laporan baru")
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
            description = "Filter: tipe (HILANG/TEMUKAN), kategori, status (AKTIF/SELESAI), lokasi, search (cari di judul/deskripsi)"
    )
    public ResponseEntity<List<LaporanResponse>> getAllLaporan(
            @Parameter(description = "Filter berdasarkan tipe: HILANG atau TEMUKAN")
            @RequestParam(required = false) String tipe,

            @Parameter(description = "Filter berdasarkan kategori: ELEKTRONIK, ALAT_TULIS, AKSESORIS_PRIBADI, ALAT_MAKAN, DOKUMEN, ATRIBUT_KAMPUS, LAINNYA")
            @RequestParam(required = false) String kategori,

            @Parameter(description = "Filter berdasarkan status: AKTIF atau SELESAI")
            @RequestParam(required = false) String status,

            @Parameter(description = "Filter berdasarkan lokasi (partial match)")
            @RequestParam(required = false) String lokasi,

            @Parameter(description = "Cari berdasarkan kata kunci di judul atau deskripsi")
            @RequestParam(required = false) String search
    ) {
        List<LaporanResponse> response = laporanService.getAllLaporan(tipe, kategori, status, lokasi, search);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detail laporan berdasarkan ID")
    public ResponseEntity<?> getLaporanById(@PathVariable Long id) {
        try {
            LaporanResponse response = laporanService.getLaporanById(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update laporan (hanya pemilik laporan)")
    public ResponseEntity<?> updateLaporan(
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
    @Operation(summary = "Hapus laporan (hanya pemilik laporan)")
    public ResponseEntity<?> deleteLaporan(@PathVariable Long id) {
        try {
            laporanService.deleteLaporan(id);
            return ResponseEntity.ok(new MessageResponse("Laporan berhasil dihapus"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}