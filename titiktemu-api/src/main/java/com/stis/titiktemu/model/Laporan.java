package com.stis.titiktemu.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "laporan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Laporan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = "Tipe laporan tidak boleh kosong")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipeLaporan tipe; // HILANG atau TEMUKAN

    @NotBlank(message = "Judul tidak boleh kosong")
    @Column(nullable = false, length = 200)
    private String judul;

    @NotBlank(message = "Deskripsi tidak boleh kosong")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String deskripsi;

    @NotNull(message = "Kategori tidak boleh kosong")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private KategoriBarang kategori;

    @NotBlank(message = "Lokasi tidak boleh kosong")
    @Column(nullable = false, length = 200)
    private String lokasi;

    @NotNull(message = "Tanggal kejadian tidak boleh kosong")
    @Column(name = "tanggal_kejadian", nullable = false)
    private LocalDate tanggalKejadian;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusLaporan status = StatusLaporan.AKTIF;

    @Column(name = "foto_url", length = 500)
    private String fotoUrl; // Untuk fitur masa depan

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Transient fields untuk response (tidak disimpan di database)
    @Transient
    @JsonProperty("pelapor_nama")
    private String pelaporNama;

    @Transient
    @JsonProperty("pelapor_jabatan")
    private String pelaporJabatan;

    @Transient
    @JsonProperty("pelapor_no_hp")
    private String pelaporNoHp;

    @Transient
    @JsonProperty("pelapor_email")
    private String pelaporEmail;

    // Enum Tipe Laporan
    public enum TipeLaporan {
        HILANG,
        TEMUKAN
    }

    // Enum Kategori Barang
    public enum KategoriBarang {
        ELEKTRONIK,
        ALAT_TULIS,
        AKSESORIS_PRIBADI,
        ALAT_MAKAN,
        DOKUMEN,
        ATRIBUT_KAMPUS,
        LAINNYA
    }

    // Enum Status Laporan
    public enum StatusLaporan {
        AKTIF,
        SELESAI
    }
}