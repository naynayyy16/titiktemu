package com.stis.titiktemu.repository;

import com.stis.titiktemu.model.Laporan;
import com.stis.titiktemu.model.Laporan.KategoriBarang;
import com.stis.titiktemu.model.Laporan.StatusLaporan;
import com.stis.titiktemu.model.Laporan.TipeLaporan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaporanRepository extends JpaRepository<Laporan, Long> {

    // Find by User ID
    List<Laporan> findByUserId(Long userId);

    // Find by Tipe
    List<Laporan> findByTipe(TipeLaporan tipe);

    // Find by Kategori
    List<Laporan> findByKategori(KategoriBarang kategori);

    // Find by Status
    List<Laporan> findByStatus(StatusLaporan status);

    // Find by Tipe and Status
    List<Laporan> findByTipeAndStatus(TipeLaporan tipe, StatusLaporan status);

    // Find by Kategori and Status
    List<Laporan> findByKategoriAndStatus(KategoriBarang kategori, StatusLaporan status);

    // Search by keyword (judul or deskripsi)
    @Query("SELECT l FROM Laporan l WHERE " +
            "LOWER(l.judul) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(l.deskripsi) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Laporan> searchByKeyword(@Param("keyword") String keyword);

    // Complex filter query
    @Query("SELECT l FROM Laporan l WHERE " +
            "(:tipe IS NULL OR l.tipe = :tipe) AND " +
            "(:kategori IS NULL OR l.kategori = :kategori) AND " +
            "(:status IS NULL OR l.status = :status) AND " +
            "(:lokasi IS NULL OR LOWER(l.lokasi) LIKE LOWER(CONCAT('%', :lokasi, '%'))) AND " +
            "(:keyword IS NULL OR LOWER(l.judul) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(l.deskripsi) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Laporan> filterLaporan(
            @Param("tipe") TipeLaporan tipe,
            @Param("kategori") KategoriBarang kategori,
            @Param("status") StatusLaporan status,
            @Param("lokasi") String lokasi,
            @Param("keyword") String keyword
    );

    // Get all laporan ordered by created_at DESC
    List<Laporan> findAllByOrderByCreatedAtDesc();
}