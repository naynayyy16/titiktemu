# Titik Temu - Lost and Found System STIS

Sistem manajemen barang hilang dan ditemukan untuk sivitas akademika Politeknik Statistika STIS.

## 📋 Deskripsi Project

**Titik Temu** adalah layanan web berbasis RESTful API yang memfasilitasi pelaporan dan pencarian barang hilang atau ditemukan di lingkungan kampus Polstat STIS. Sistem ini menggantikan metode konvensional (grup chat) dengan platform terorganisir yang memudahkan proses pengembalian barang kepada pemiliknya.

### Fitur Utama
✅ Manajemen User (Register, Login, Profile, Change Password, Delete Account)  
✅ Laporan Barang Hilang & Ditemukan  
✅ Filter & Search Laporan  
✅ JWT Token-Based Authentication  
✅ Dokumentasi API dengan Swagger/OpenAPI  
✅ Info Kontak Pelapor untuk koordinasi  

---

## 🛠️ Tech Stack

- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17
- **Database**: MySQL
- **Authentication**: JWT (JSON Web Token)
- **Documentation**: Swagger/OpenAPI
- **Build Tool**: Maven

---

## 📁 Struktur Project

```
titik-temu/
├── src/main/java/com/stis/titiktemu/
│   ├── config/
│   │   ├── OpenApiConfig.java
│   │   └── SecurityConfig.java
│   ├── controller/
│   │   ├── AuthController.java
│   │   ├── UserController.java
│   │   └── LaporanController.java
│   ├── dto/
│   │   └── [DTOs].java
│   ├── exception/
│   │   └── GlobalExceptionHandler.java
│   ├── model/
│   │   ├── User.java
│   │   └── Laporan.java
│   ├── repository/
│   │   ├── UserRepository.java
│   │   └── LaporanRepository.java
│   ├── security/
│   │   ├── JwtUtil.java
│   │   ├── JwtAuthenticationFilter.java
│   │   └── CustomUserDetailsService.java
│   ├── service/
│   │   ├── AuthService.java
│   │   ├── UserService.java
│   │   └── LaporanService.java
│   └── TitikTemuApplication.java
├── src/main/resources/
│   └── application.properties
└── pom.xml
```

---

## 🚀 Cara Menjalankan Project

### 1. Persiapan Database
1. Buka phpMyAdmin
2. Import file `schema.sql` atau jalankan query SQL yang telah disediakan
3. Database `titik_temu` akan otomatis terbuat beserta tabel-tabelnya

### 2. Konfigurasi Database
Edit file `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/titik_temu
spring.datasource.username=root
spring.datasource.password=     # Sesuaikan dengan password MySQL Anda
```

### 3. Install Dependencies
```bash
mvn clean install
```

### 4. Run Application
```bash
mvn spring-boot:run
```

Atau jalankan langsung dari IDE (IntelliJ IDEA / Eclipse):
- Klik kanan pada `TitikTemuApplication.java`
- Pilih "Run"

### 5. Akses API
- **Base URL**: `http://localhost:8080/api`
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **API Docs**: `http://localhost:8080/api-docs`

---

## 📖 API Endpoints

### 🔐 Authentication (Public)

#### Register
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "budi123",
  "email": "budi@stis.ac.id",
  "password": "password123",
  "namaLengkap": "Budi Setiawan",
  "jabatan": "Mahasiswa",
  "nimNip": "222011456",
  "noHp": "081234567890"
}
```

**Response (201 Created):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "username": "budi123",
  "email": "budi@stis.ac.id",
  "namaLengkap": "Budi Setiawan",
  "message": "Login berhasil"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "budi123",
  "password": "password123"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "username": "budi123",
  "email": "budi@stis.ac.id",
  "namaLengkap": "Budi Setiawan",
  "message": "Login berhasil"
}
```

---

### 👤 User Management (Protected)

**Header Required:**
```
Authorization: Bearer {token}
```

#### Get Profile
```http
GET /api/users/profile
```

#### Update Profile
```http
PUT /api/users/profile
Content-Type: application/json

{
  "namaLengkap": "Budi Setiawan Updated",
  "jabatan": "Mahasiswa",
  "nimNip": "222011456",
  "noHp": "081234567899",
  "email": "budi@stis.ac.id"
}
```

#### Change Password
```http
PUT /api/users/change-password
Content-Type: application/json

{
  "oldPassword": "password123",
  "newPassword": "newpassword456"
}
```

#### Delete Account
```http
DELETE /api/users/account
```

---

### 📝 Laporan Management (Protected)

**Header Required:**
```
Authorization: Bearer {token}
```

#### Create Laporan
```http
POST /api/laporan
Content-Type: application/json

{
  "tipe": "HILANG",
  "judul": "iPhone 14 Pro Max Warna Ungu",
  "deskripsi": "Hilang di area kantin. Casing hitam dengan stiker STIS.",
  "kategori": "ELEKTRONIK",
  "lokasi": "Kantin STIS",
  "tanggalKejadian": "2025-10-24"
}
```

**Enum Values:**
- **tipe**: `HILANG`, `TEMUKAN`
- **kategori**: `ELEKTRONIK`, `ALAT_TULIS`, `AKSESORIS_PRIBADI`, `ALAT_MAKAN`, `DOKUMEN`, `ATRIBUT_KAMPUS`, `LAINNYA`
- **status**: `AKTIF`, `SELESAI`

#### Get All Laporan (dengan Filter)
```http
GET /api/laporan
GET /api/laporan?tipe=HILANG
GET /api/laporan?kategori=ELEKTRONIK
GET /api/laporan?status=AKTIF
GET /api/laporan?search=iphone
GET /api/laporan?tipe=HILANG&kategori=ELEKTRONIK&status=AKTIF
```

**Response:**
```json
[
  {
    "id": 1,
    "tipe": "HILANG",
    "judul": "iPhone 14 Pro Max Warna Ungu",
    "deskripsi": "Hilang di area kantin...",
    "kategori": "ELEKTRONIK",
    "lokasi": "Kantin STIS",
    "tanggalKejadian": "2025-10-24",
    "status": "AKTIF",
    "fotoUrl": null,
    "pelaporNama": "Budi Setiawan",
    "pelaporJabatan": "Mahasiswa",
    "pelaporNoHp": "081234567890",
    "pelaporEmail": "budi@stis.ac.id",
    "createdAt": "2025-10-25T10:30:00",
    "updatedAt": "2025-10-25T10:30:00"
  }
]
```

#### Get Laporan by ID
```http
GET /api/laporan/{id}
```

#### Update Laporan (Hanya Pemilik)
```http
PUT /api/laporan/{id}
Content-Type: application/json

{
  "judul": "iPhone 14 Pro Max (UPDATED)",
  "deskripsi": "UPDATE: Sudah ditemukan",
  "status": "SELESAI"
}
```

#### Delete Laporan (Hanya Pemilik)
```http
DELETE /api/laporan/{id}
```

---

## 🔒 Security

### JWT Token
- Token expired: 24 jam
- Algorithm: HS256
- Secret key: (ada di `application.properties`)

### Authorization
- Semua endpoint (kecuali `/auth/**`) memerlukan token JWT
- Token dikirim via header: `Authorization: Bearer {token}`
- User hanya bisa update/delete laporan milik sendiri

---

## 📊 Database Schema

### Table: users
| Column | Type | Constraint |
|--------|------|------------|
| id | BIGINT | PK, AUTO_INCREMENT |
| username | VARCHAR(50) | UNIQUE, NOT NULL |
| email | VARCHAR(100) | UNIQUE, NOT NULL |
| password | VARCHAR(255) | NOT NULL (hashed) |
| nama_lengkap | VARCHAR(100) | NOT NULL |
| jabatan | VARCHAR(50) | NOT NULL |
| nim_nip | VARCHAR(20) | NULL |
| no_hp | VARCHAR(20) | NOT NULL |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP |
| updated_at | TIMESTAMP | ON UPDATE CURRENT_TIMESTAMP |

### Table: laporan
| Column | Type | Constraint |
|--------|------|------------|
| id | BIGINT | PK, AUTO_INCREMENT |
| user_id | BIGINT | FK → users(id) |
| tipe | ENUM | HILANG, TEMUKAN |
| judul | VARCHAR(200) | NOT NULL |
| deskripsi | TEXT | NOT NULL |
| kategori | ENUM | 7 kategori |
| lokasi | VARCHAR(200) | NOT NULL |
| tanggal_kejadian | DATE | NOT NULL |
| status | ENUM | AKTIF, SELESAI |
| foto_url | VARCHAR(500) | NULL |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP |
| updated_at | TIMESTAMP | ON UPDATE CURRENT_TIMESTAMP |

---

## 📦 Dependencies (pom.xml)

```xml
<!-- Spring Boot Starter Web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- Spring Boot Data JPA -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- Spring Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- MySQL Driver -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
</dependency>

<!-- JWT -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.3</version>
</dependency>

<!-- Swagger/OpenAPI -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.3.0</version>
</dependency>

<!-- Lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
```

---

## 🎯 Fitur Unggulan

### 1. Filter & Search yang Powerful
```http
# Cari barang hilang kategori elektronik yang masih aktif
GET /api/laporan?tipe=HILANG&kategori=ELEKTRONIK&status=AKTIF

# Search keyword di judul atau deskripsi
GET /api/laporan?search=iphone

# Filter berdasarkan lokasi
GET /api/laporan?lokasi=perpustakaan
```

### 2. Info Kontak Pelapor
Setiap response laporan menyertakan:
- Nama lengkap pelapor
- Jabatan (Mahasiswa/Dosen/dll)
- Nomor HP
- Email

User bisa langsung menghubungi pelapor untuk koordinasi pengambilan barang.

### 3. Authorization yang Ketat
- Hanya pemilik laporan yang bisa update/delete
- Token JWT expired 24 jam
- Password di-hash dengan BCrypt

---

## 👨‍💻 Author

**Safira Inayah**  
Ujian Tengah Semester
Mata Kuliah: Pemrograman Platform Khusus  

---

**Terima Kasih!**