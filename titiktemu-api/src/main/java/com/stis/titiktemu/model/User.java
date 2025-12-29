package com.stis.titiktemu.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username tidak boleh kosong")
    @Size(min = 3, max = 50, message = "Username harus 3-50 karakter")
    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @NotBlank(message = "Email tidak boleh kosong")
    @Email(message = "Format email tidak valid")
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @NotBlank(message = "Password tidak boleh kosong")
    @Size(min = 6, message = "Password minimal 6 karakter")
    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @NotBlank(message = "Nama lengkap tidak boleh kosong")
    @Column(name = "nama_lengkap", nullable = false, length = 100)
    private String namaLengkap;

    @NotBlank(message = "Jabatan tidak boleh kosong")
    @Column(nullable = false, length = 50)
    private String jabatan; // Mahasiswa, Dosen, Tendik, Cleaning Service, dll

    @Column(name = "nim_nip", length = 20)
    private String nimNip; // Nullable

    @NotBlank(message = "Nomor HP tidak boleh kosong")
    @Column(name = "no_hp", nullable = false, length = 20)
    private String noHp;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Laporan> laporanList = new ArrayList<>();
}