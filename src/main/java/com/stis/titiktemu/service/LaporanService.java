package com.stis.titiktemu.service;

import com.stis.titiktemu.dto.LaporanRequest;
import com.stis.titiktemu.dto.LaporanResponse;
import com.stis.titiktemu.dto.UpdateLaporanRequest;
import com.stis.titiktemu.model.Laporan;
import com.stis.titiktemu.model.Laporan.KategoriBarang;
import com.stis.titiktemu.model.Laporan.StatusLaporan;
import com.stis.titiktemu.model.Laporan.TipeLaporan;
import com.stis.titiktemu.model.User;
import com.stis.titiktemu.repository.LaporanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LaporanService {

    @Autowired
    private LaporanRepository laporanRepository;

    @Autowired
    private UserService userService;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Create laporan
    // Menambahkan @Transactional adalah best practice untuk operasi tulis (create)
    @Transactional
    public LaporanResponse createLaporan(LaporanRequest request) {
        User currentUser = userService.getCurrentUser();

        Laporan laporan = new Laporan();
        laporan.setUser(currentUser);
        laporan.setTipe(TipeLaporan.valueOf(request.getTipe().toUpperCase()));
        laporan.setJudul(request.getJudul());
        laporan.setDeskripsi(request.getDeskripsi());
        laporan.setKategori(KategoriBarang.valueOf(request.getKategori().toUpperCase()));
        laporan.setLokasi(request.getLokasi());
        laporan.setTanggalKejadian(LocalDate.parse(request.getTanggalKejadian(), dateFormatter));
        laporan.setStatus(StatusLaporan.AKTIF);

        laporanRepository.save(laporan);

        return mapToLaporanResponse(laporan);
    }

    // Get all laporan (method read-only)
    @Transactional(readOnly = true) // <-- FIX UNTUK GET ALL
    public List<LaporanResponse> getAllLaporan(String tipe, String kategori, String status, String lokasi, String search) {
        TipeLaporan tipeLaporan = (tipe != null && !tipe.isEmpty()) ? TipeLaporan.valueOf(tipe.toUpperCase()) : null;
        KategoriBarang kategoriBarang = (kategori != null && !kategori.isEmpty()) ? KategoriBarang.valueOf(kategori.toUpperCase()) : null;
        StatusLaporan statusLaporan = (status != null && !status.isEmpty()) ? StatusLaporan.valueOf(status.toUpperCase()) : null;

        List<Laporan> laporanList;

        if (tipeLaporan != null || kategoriBarang != null || statusLaporan != null ||
                (lokasi != null && !lokasi.isEmpty()) || (search != null && !search.isEmpty())) {
            laporanList = laporanRepository.filterLaporan(tipeLaporan, kategoriBarang, statusLaporan, lokasi, search);
        } else {
            laporanList = laporanRepository.findAllByOrderByCreatedAtDesc();
        }

        return laporanList.stream()
                .map(this::mapToLaporanResponse)
                .collect(Collectors.toList());
    }

    // Get laporan by ID (method read-only)
    @Transactional(readOnly = true) // <-- FIX UNTUK GET BY ID
    public LaporanResponse getLaporanById(Long id) {
        Laporan laporan = laporanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Laporan tidak ditemukan"));

        return mapToLaporanResponse(laporan);
    }

    // Update laporan (method tulis/write)
    @Transactional // <-- FIX UNTUK UPDATE (PUT). (Perhatikan: *bukan* readOnly=true)
    public LaporanResponse updateLaporan(Long id, UpdateLaporanRequest request) {
        User currentUser = userService.getCurrentUser();

        // Sesi akan tetap terbuka selama method ini berjalan
        Laporan laporan = laporanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Laporan tidak ditemukan"));

        // Verifikasi kepemilikan (sekarang aman karena sesi masih terbuka)
        if (!laporan.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Anda tidak memiliki akses untuk mengupdate laporan ini");
        }

        // Update fields if provided
        if (request.getJudul() != null && !request.getJudul().isEmpty()) {
            laporan.setJudul(request.getJudul());
        }
        if (request.getDeskripsi() != null && !request.getDeskripsi().isEmpty()) {
            laporan.setDeskripsi(request.getDeskripsi());
        }
        if (request.getKategori() != null && !request.getKategori().isEmpty()) {
            laporan.setKategori(KategoriBarang.valueOf(request.getKategori().toUpperCase()));
        }
        if (request.getLokasi() != null && !request.getLokasi().isEmpty()) {
            laporan.setLokasi(request.getLokasi());
        }
        if (request.getTanggalKejadian() != null && !request.getTanggalKejadian().isEmpty()) {
            laporan.setTanggalKejadian(LocalDate.parse(request.getTanggalKejadian(), dateFormatter));
        }
        if (request.getStatus() != null && !request.getStatus().isEmpty()) {
            laporan.setStatus(StatusLaporan.valueOf(request.getStatus().toUpperCase()));
        }

        laporanRepository.save(laporan);

        return mapToLaporanResponse(laporan);
    }

    // Delete laporan (method tulis/write)
    @Transactional // <-- FIX UNTUK DELETE (juga memiliki verifikasi kepemilikan)
    public void deleteLaporan(Long id) {
        User currentUser = userService.getCurrentUser();

        // Sesi akan tetap terbuka selama method ini berjalan
        Laporan laporan = laporanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Laporan tidak ditemukan"));

        // Verifikasi kepemilikan (sekarang aman karena sesi masih terbuka)
        if (!laporan.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Anda tidak memiliki akses untuk menghapus laporan ini");
        }

        laporanRepository.delete(laporan);
    }

    // Helper method to map Laporan to LaporanResponse
    // (Tidak perlu @Transactional karena dipanggil oleh method yang sudah @Transactional)
    private LaporanResponse mapToLaporanResponse(Laporan laporan) {
        LaporanResponse response = new LaporanResponse();
        response.setId(laporan.getId());
        response.setTipe(laporan.getTipe().name());
        response.setJudul(laporan.getJudul());
        response.setDeskripsi(laporan.getDeskripsi());
        response.setKategori(laporan.getKategori().name());
        response.setLokasi(laporan.getLokasi());
        response.setTanggalKejadian(laporan.getTanggalKejadian().toString());
        response.setStatus(laporan.getStatus().name());
        response.setFotoUrl(laporan.getFotoUrl());

        // Set pelapor info
        response.setPelaporNama(laporan.getUser().getNamaLengkap());
        response.setPelaporJabatan(laporan.getUser().getJabatan());
        response.setPelaporNoHp(laporan.getUser().getNoHp());
        response.setPelaporEmail(laporan.getUser().getEmail());

        response.setCreatedAt(laporan.getCreatedAt().toString());
        response.setUpdatedAt(laporan.getUpdatedAt().toString());

        return response;
    }
}