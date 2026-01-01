# Titik Temu API - Backend REST API

Backend REST API untuk aplikasi Lost & Found "Titik Temu" menggunakan **Spring Boot** dengan **Java 17**.

---

## 📋 Deskripsi

Titik Temu API adalah layanan backend berbasis RESTful API yang menyediakan endpoint untuk manajemen user dan laporan barang hilang/ditemukan. API ini menggunakan JWT (JSON Web Token) untuk autentikasi dan authorization.

---

## 🛠️ Tech Stack

- **Framework**: Spring Boot 3.5.7
- **Language**: Java 17
- **Database**: MySQL 8.0
- **ORM**: Spring Data JPA (Hibernate)
- **Security**: Spring Security + JWT
- **Validation**: Jakarta Bean Validation
- **Documentation**: SpringDoc OpenAPI (Swagger)
- **Build Tool**: Apache Maven
- **Password Encryption**: BCrypt

### Dependencies Utama
- `spring-boot-starter-web` - REST API
- `spring-boot-starter-data-jpa` - Database ORM
- `spring-boot-starter-security` - Security & Authentication
- `spring-boot-starter-validation` - Input validation
- `mysql-connector-j` - MySQL driver
- `jjwt` (v0.12.3) - JWT token handling
- `springdoc-openapi-starter-webmvc-ui` - API documentation

---

## 📁 Struktur Project

```
titiktemu-api/
├── src/
│   ├── main/
│   │   ├── java/com/stis/titiktemu/
│   │   │   ├── config/
│   │   │   │   ├── OpenApiConfig.java           # Konfigurasi Swagger/OpenAPI
│   │   │   │   ├── SecurityConfig.java          # Spring Security config
│   │   │   │   └── WebConfig.java               # CORS & Web config
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java          # Auth endpoints (login, register)
│   │   │   │   ├── UserController.java          # User management endpoints
│   │   │   │   └── LaporanController.java       # Laporan CRUD endpoints
│   │   │   ├── dto/
│   │   │   │   ├── request/                     # Request DTOs
│   │   │   │   │   ├── LoginRequest.java
│   │   │   │   │   ├── RegisterRequest.java
│   │   │   │   │   ├── ChangePasswordRequest.java
│   │   │   │   │   └── LaporanRequest.java
│   │   │   │   └── response/                    # Response DTOs
│   │   │   │       ├── AuthResponse.java
│   │   │   │       ├── UserResponse.java
│   │   │   │       └── LaporanResponse.java
│   │   │   ├── exception/
│   │   │   │   ├── GlobalExceptionHandler.java  # Centralized exception handling
│   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   └── UnauthorizedException.java
│   │   │   ├── model/
│   │   │   │   ├── User.java                    # User entity
│   │   │   │   └── Laporan.java                 # Laporan entity
│   │   │   ├── repository/
│   │   │   │   ├── UserRepository.java          # User data access
│   │   │   │   └── LaporanRepository.java       # Laporan data access
│   │   │   ├── security/
│   │   │   │   ├── JwtUtil.java                 # JWT token utility
│   │   │   │   ├── JwtAuthenticationFilter.java # JWT filter
│   │   │   │   └── CustomUserDetailsService.java # UserDetails implementation
│   │   │   ├── service/
│   │   │   │   ├── AuthService.java             # Authentication logic
│   │   │   │   ├── UserService.java             # User business logic
│   │   │   │   └── LaporanService.java          # Laporan business logic
│   │   │   └── TitikTemuApplication.java        # Main application
│   │   └── resources/
│   │       └── application.properties           # Application config
│   └── test/
│       └── java/com/stis/titiktemu/             # Unit tests
├── uploads/                                      # Folder untuk file upload
├── target/                                       # Build output
├── db_schema                                     # Database schema SQL
├── pom.xml                                       # Maven dependencies
├── mvnw                                          # Maven wrapper (Unix)
└── mvnw.cmd                                      # Maven wrapper (Windows)
```

---

## 🚀 Instalasi & Setup

### Prasyarat
- **Java Development Kit (JDK) 17** atau lebih baru
- **MySQL 8.0** atau lebih baru
- **Maven 3.6+** (opsional, bisa pakai Maven Wrapper)

### 1️⃣ Setup Database

**📌 METODE 1: Auto-Create (RECOMMENDED)**

Cara paling mudah! Cukup buat database kosong, tabel akan otomatis dibuat.

1. Jalankan MySQL Server
2. Buat database kosong:

**Via phpMyAdmin:**
- Buka phpMyAdmin
- Klik **New** (Database baru)
- Nama: `titik_temu`
- Collation: `utf8mb4_general_ci`
- Klik **Create**

**Via MySQL Command Line:**
```bash
mysql -u root -p
```
```sql
CREATE DATABASE titik_temu;
EXIT;
```

3. **SELESAI!** Tabel akan otomatis dibuat saat aplikasi pertama running

Konfigurasi `spring.jpa.hibernate.ddl-auto=update` akan:
- ✅ Auto-create tabel `users` dan `laporan`
- ✅ Auto-create view `v_laporan_detail`
- ✅ Auto-update schema jika ada perubahan model

---

**📌 METODE 2: Import Manual (OPSIONAL)**

Gunakan jika ingin sample data untuk testing/development.

**Via phpMyAdmin:**
- Buka phpMyAdmin
- Klik tab **Import**
- Choose File: Pilih file `db_schema`
- Klik **Go/Execute**
- Database + tabel + **10 sample data** akan terbuat

**Via MySQL Command Line:**
```bash
mysql -u root -p < db_schema
```

**Sample Data Included:**
- 5 users (mahasiswa, dosen, tendik, cleaning service)
- 10 laporan (mix hilang & temukan, berbagai kategori)
- Login test: username `mahasiswa1`, password `password123`

---

**Verifikasi Database:**
```sql
SHOW DATABASES;  -- Harus ada 'titik_temu'
USE titik_temu;
SHOW TABLES;     -- Harus ada 'users', 'laporan', 'v_laporan_detail'
SELECT COUNT(*) FROM users;    -- Jika import manual: 5 users
SELECT COUNT(*) FROM laporan;  -- Jika import manual: 10 laporan
```

### 2️⃣ Konfigurasi Application

Edit file **`src/main/resources/application.properties`**:

```properties
# Database Configuration - WAJIB DISESUAIKAN!
spring.datasource.url=jdbc:mysql://localhost:3306/titik_temu?useSSL=false&serverTimezone=Asia/Jakarta&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=                     # ISI PASSWORD MYSQL ANDA DI SINI!

# JWT Secret - Bisa diganti dengan secret Anda sendiri
jwt.secret=TitikTemuSTISSecretKeyForJWTAuthenticationVeryLongAndSecure2025
jwt.expiration=86400000                         # 24 jam dalam milliseconds

# Upload Path
upload.path=uploads                             # Folder untuk upload foto
```

**⚠️ PENTING:**
- Sesuaikan `spring.datasource.password` dengan password MySQL Anda
- Jika password kosong, biarkan nilai tersebut kosong
- Pastikan database `titik_temu` sudah dibuat

### 3️⃣ Install Dependencies

Menggunakan Maven Wrapper (recommended):
```bash
# Windows
mvnw.cmd clean install

# Linux/Mac
./mvnw clean install
```

Atau menggunakan Maven global:
```bash
mvn clean install
```

Proses ini akan:
- Download semua dependencies
- Compile source code
- Run tests
- Package aplikasi menjadi JAR

### 4️⃣ Run Application

**Opsi A: Maven Wrapper**
```bash
# Windows
mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

**Opsi B: Maven Global**
```bash
mvn spring-boot:run
```

**Opsi C: Run dari IDE**
- **IntelliJ IDEA**: Klik kanan `TitikTemuApplication.java` → Run
- **Eclipse**: Klik kanan `TitikTemuApplication.java` → Run As → Java Application
- **VS Code**: Jalankan dari Spring Boot Dashboard

**Opsi D: Run JAR file**
```bash
# Setelah build
java -jar target/titiktemu-api-0.0.1-SNAPSHOT.jar
```

### 5️⃣ Verifikasi

Jika berhasil, akan muncul log seperti:
```
Started TitikTemuApplication in X.XXX seconds
Tomcat started on port(s): 8080 (http)
```

**Akses URL berikut:**
- **API Base**: http://localhost:8080/api
- **Swagger UI**: http://localhost:8080/docs/swagger-ui
- **OpenAPI JSON**: http://localhost:8080/docs/open-api

---

## 📖 Dokumentasi API

### Authentication Endpoints

| Method | Endpoint | Deskripsi | Auth Required |
|--------|----------|-----------|---------------|
| POST | `/api/auth/register` | Register user baru | ❌ |
| POST | `/api/auth/login` | Login dan dapatkan JWT token | ❌ |

**Register Request Body:**
```json
{
  "username": "john_doe",
  "email": "john@stis.ac.id",
  "password": "Password123!",
  "namaLengkap": "John Doe",
  "jabatan": "Mahasiswa",
  "nimNip": "222011234",
  "noHp": "081234567890"
}
```

**Login Request Body:**
```json
{
  "username": "john_doe",
  "password": "Password123!"
}
```

**Login Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "username": "john_doe",
  "email": "john@stis.ac.id"
}
```

### User Management Endpoints

| Method | Endpoint | Deskripsi | Auth Required |
|--------|----------|-----------|---------------|
| GET | `/api/users/profile` | Get profil user yang login | ✅ |
| PUT | `/api/users/profile` | Update profil user | ✅ |
| PUT | `/api/users/change-password` | Ganti password | ✅ |
| DELETE | `/api/users/delete` | Hapus akun user | ✅ |

**Update Profile Request:**
```json
{
  "namaLengkap": "John Doe Updated",
  "jabatan": "Mahasiswa",
  "nimNip": "222011234",
  "noHp": "081234567891"
}
```

**Change Password Request:**
```json
{
  "oldPassword": "Password123!",
  "newPassword": "NewPassword456!"
}
```

### Laporan Endpoints

| Method | Endpoint | Deskripsi | Auth Required |
|--------|----------|-----------|---------------|
| GET | `/api/laporan` | Get semua laporan (paged) | ✅ |
| GET | `/api/laporan/{id}` | Get laporan by ID | ✅ |
| POST | `/api/laporan` | Create laporan baru | ✅ |
| PUT | `/api/laporan/{id}` | Update laporan | ✅ |
| DELETE | `/api/laporan/{id}` | Delete laporan | ✅ |
| GET | `/api/laporan/filter` | Filter laporan by tipe/kategori/status | ✅ |

**Create Laporan Request (multipart/form-data):**
```json
{
  "tipe": "HILANG",
  "judul": "iPhone 13 Pro Biru",
  "deskripsi": "Hilang di kantin, casing hitam",
  "kategori": "ELEKTRONIK",
  "lokasi": "Kantin STIS",
  "tanggalKejadian": "2025-01-01",
  "foto": [file]  // Optional
}
```

**Filter Parameters:**
- `tipe`: HILANG / TEMUKAN
- `kategori`: ELEKTRONIK, ALAT_TULIS, AKSESORIS_PRIBADI, ALAT_MAKAN, DOKUMEN, ATRIBUT_KAMPUS, LAINNYA
- `status`: AKTIF / SELESAI
- `page`: page number (default: 0)
- `size`: items per page (default: 10)

**Laporan Response:**
```json
{
  "id": 1,
  "tipe": "HILANG",
  "judul": "iPhone 13 Pro Biru",
  "deskripsi": "Hilang di kantin, casing hitam",
  "kategori": "ELEKTRONIK",
  "lokasi": "Kantin STIS",
  "tanggalKejadian": "2025-01-01",
  "status": "AKTIF",
  "fotoUrl": "http://localhost:8080/uploads/abc123.jpg",
  "createdAt": "2025-01-01T10:00:00",
  "updatedAt": "2025-01-01T10:00:00",
  "pelapor": {
    "id": 1,
    "username": "john_doe",
    "namaLengkap": "John Doe",
    "jabatan": "Mahasiswa",
    "noHp": "081234567890",
    "email": "john@stis.ac.id"
  }
}
```

---

## 🔐 Authentication & Security

### JWT Token Usage

Setelah login berhasil, simpan token yang diterima dan gunakan di setiap request:

**Header:**
```
Authorization: Bearer <your-token-here>
```

**Contoh Request dengan cURL:**
```bash
curl -X GET http://localhost:8080/api/users/profile \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

### Token Expiration
- Token berlaku selama **24 jam**
- Setelah expired, user harus login ulang
- Token disimpan di client-side (mobile app: DataStore)

### Security Features
- Password di-hash menggunakan **BCrypt** (strength 10)
- JWT secret key configurable via `application.properties`
- CORS enabled untuk development
- Input validation menggunakan Jakarta Bean Validation
- Global exception handling untuk error response konsisten

---

## 📂 Database Schema

### Tabel `users`

| Column | Type | Constraint | Deskripsi |
|--------|------|------------|-----------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | User ID |
| username | VARCHAR(50) | UNIQUE, NOT NULL | Username untuk login |
| email | VARCHAR(100) | UNIQUE, NOT NULL | Email user |
| password | VARCHAR(255) | NOT NULL | Password (BCrypt hashed) |
| nama_lengkap | VARCHAR(100) | NOT NULL | Nama lengkap user |
| jabatan | VARCHAR(50) | NOT NULL | Mahasiswa/Dosen/Tendik/dll |
| nim_nip | VARCHAR(20) | NULL | NIM/NIP (opsional) |
| no_hp | VARCHAR(20) | NOT NULL | Nomor HP |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Waktu dibuat |
| updated_at | TIMESTAMP | ON UPDATE CURRENT_TIMESTAMP | Waktu update |

### Tabel `laporan`

| Column | Type | Constraint | Deskripsi |
|--------|------|------------|-----------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Laporan ID |
| user_id | BIGINT | FOREIGN KEY → users(id) | ID pembuat laporan |
| tipe | ENUM | 'HILANG', 'TEMUKAN' | Tipe laporan |
| judul | VARCHAR(200) | NOT NULL | Judul laporan |
| deskripsi | TEXT | NOT NULL | Deskripsi detail |
| kategori | ENUM | (7 kategori) | Kategori barang |
| lokasi | VARCHAR(200) | NOT NULL | Lokasi kejadian |
| tanggal_kejadian | DATE | NOT NULL | Tanggal kejadian |
| status | ENUM | 'AKTIF', 'SELESAI' | Status laporan |
| foto_url | VARCHAR(500) | NULL | Path foto upload |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Waktu dibuat |
| updated_at | TIMESTAMP | ON UPDATE CURRENT_TIMESTAMP | Waktu update |

**Kategori Barang:**
- ELEKTRONIK
- ALAT_TULIS
- AKSESORIS_PRIBADI
- ALAT_MAKAN
- DOKUMEN
- ATRIBUT_KAMPUS
- LAINNYA

---

## 🧪 Testing

### Run Unit Tests
```bash
mvnw test
# atau
mvn test
```

### Manual Testing dengan Swagger
1. Buka http://localhost:8080/docs/swagger-ui
2. Test endpoint `/api/auth/register` untuk buat user
3. Test endpoint `/api/auth/login` untuk dapatkan token
4. Klik tombol **Authorize** di Swagger UI
5. Masukkan token dengan format: `Bearer <token>`
6. Test endpoint lainnya yang memerlukan autentikasi

### Testing dengan Postman
1. Import collection (bisa dibuat dari OpenAPI spec)
2. Set environment variable `baseUrl` = `http://localhost:8080/api`
3. Set environment variable `token` dari response login
4. Gunakan `{{token}}` di header Authorization

---

## 📦 Build & Deployment

### Build JAR File
```bash
mvnw clean package
# Output: target/titiktemu-api-0.0.1-SNAPSHOT.jar
```

### Run JAR File
```bash
java -jar target/titiktemu-api-0.0.1-SNAPSHOT.jar
```

### Production Configuration
Untuk production, update `application.properties`:
```properties
# Ubah ke production database
spring.datasource.url=jdbc:mysql://production-host:3306/titik_temu

# Disable show SQL
spring.jpa.show-sql=false

# Change logging level
logging.level.root=WARN
logging.level.com.stis.titiktemu=INFO

# Gunakan secret yang lebih kuat
jwt.secret=<generate-random-256-bit-key>
```

---

## 🔧 Troubleshooting

### Error: Database Connection Failed
**Penyebab:** MySQL tidak running atau konfigurasi salah

**Solusi:**
1. Pastikan MySQL service running
2. Cek username/password di `application.properties`
3. Pastikan database `titik_temu` sudah dibuat
4. Test koneksi: `mysql -u root -p`

### Error: Port 8080 already in use
**Penyebab:** Port 8080 digunakan aplikasi lain

**Solusi:**
Ubah port di `application.properties`:
```properties
server.port=8081
```

### Error: JWT token expired
**Penyebab:** Token sudah melewati 24 jam

**Solusi:**
- Login ulang untuk mendapatkan token baru
- Atau ubah `jwt.expiration` di config (dalam milliseconds)

### Error: File upload failed
**Penyebab:** Folder `uploads/` tidak ada atau tidak writable

**Solusi:**
```bash
mkdir uploads
chmod 755 uploads  # Linux/Mac
```

---

## 📝 Notes

- **CORS**: Saat ini dikonfigurasi untuk allow all origins (development). Untuk production, batasi origin yang diizinkan.
- **File Upload**: Maksimal 10MB per file. Bisa diubah di `application.properties`.
- **Timezone**: Semua timestamp menggunakan Asia/Jakarta timezone.
- **Database**: Menggunakan `spring.jpa.hibernate.ddl-auto=update` (auto-create/update tables).

---

## 🔗 Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [JWT Official](https://jwt.io/)
- [SpringDoc OpenAPI](https://springdoc.org/)
- [MySQL Documentation](https://dev.mysql.com/doc/)

---

## 👨‍💻 Development

Untuk development:
1. Gunakan IDE dengan Spring support (IntelliJ IDEA recommended)
2. Install Lombok plugin jika menggunakan Lombok
3. Enable auto-reload dengan Spring Boot DevTools (sudah included)
4. Gunakan Swagger UI untuk testing API

---

**Developed with ❤️ for STIS Academic Community**
