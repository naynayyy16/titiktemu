# Titik Temu - Lost and Found System STIS

Sistem manajemen barang hilang dan ditemukan untuk sivitas akademika Politeknik Statistika STIS.

## 📋 Deskripsi Project

**Titik Temu** adalah platform Lost & Found berbasis mobile dan web yang memfasilitasi pelaporan dan pencarian barang hilang atau ditemukan di lingkungan kampus Polstat STIS. Sistem ini menggantikan metode konvensional (grup chat) dengan platform terorganisir yang memudahkan proses pengembalian barang kepada pemiliknya.

### Fitur Utama
✅ Manajemen User (Register, Login, Profile, Change Password, Delete Account)  
✅ Laporan Barang Hilang & Ditemukan  
✅ Filter & Search Laporan berdasarkan kategori dan tipe  
✅ JWT Token-Based Authentication  
✅ Upload & Display Foto Barang  
✅ Dokumentasi API dengan Swagger/OpenAPI  
✅ Info Kontak Pelapor untuk koordinasi langsung via WhatsApp  
✅ Aplikasi Mobile Android dengan Jetpack Compose  

---

## 🛠️ Tech Stack

### Backend (API)
- **Framework**: Spring Boot 3.5.7
- **Language**: Java 17
- **Database**: MySQL 8.0
- **Authentication**: JWT (JSON Web Token)
- **Documentation**: Swagger/OpenAPI (SpringDoc)
- **Build Tool**: Maven
- **File Upload**: Multipart/form-data

### Mobile (Android)
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose (Material 3)
- **Architecture**: MVVM + Repository Pattern
- **Networking**: Retrofit + OkHttp
- **Async**: Kotlin Coroutines + Flow
- **Local Storage**: DataStore (encrypted)
- **Build Tool**: Gradle (Kotlin DSL)

---

## 📁 Struktur Project

```
titiktemu/
├── titiktemu-api/              # Backend REST API (Spring Boot)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/stis/titiktemu/
│   │   │   │   ├── config/
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   ├── exception/
│   │   │   │   ├── model/
│   │   │   │   ├── repository/
│   │   │   │   ├── security/
│   │   │   │   ├── service/
│   │   │   │   └── TitikTemuApplication.java
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   ├── uploads/                # Folder untuk foto upload
│   ├── db_schema               # Database schema SQL
│   ├── pom.xml
│   └── README.md
│
├── titiktemu-mobile/           # Android Mobile App (Kotlin)
│   ├── app/
│   │   ├── src/
│   │   │   └── main/
│   │   │       ├── java/com/stis/titiktemu/
│   │   │       │   ├── data/
│   │   │       │   │   ├── api/
│   │   │       │   │   ├── model/
│   │   │       │   │   └── repository/
│   │   │       │   ├── ui/
│   │   │       │   │   ├── screens/
│   │   │       │   │   ├── components/
│   │   │       │   │   └── theme/
│   │   │       │   ├── util/
│   │   │       │   ├── MainActivity.kt
│   │   │       │   └── TitikTemuApp.kt
│   │   │       ├── res/
│   │   │       └── AndroidManifest.xml
│   │   └── build.gradle.kts
│   ├── build.gradle.kts
│   └── README.md
│
├── uploads/                    # Folder shared upload
└── README.md                   # File ini
```

---

## 🚀 Instalasi & Setup

### Prasyarat
- **Java Development Kit (JDK) 17** atau lebih baru
- **MySQL 8.0** atau lebih baru
- **Maven 3.6+** (atau gunakan Maven Wrapper `mvnw` yang sudah disediakan)
- **Android Studio** (untuk mobile app)
- **Git** (untuk clone repository)

---

## 📝 Langkah Instalasi

### 1️⃣ Clone Repository
```bash
git clone <repository-url>
cd titiktemu
```

### 2️⃣ Setup Database

**Metode 1: Auto-Create (RECOMMENDED - Paling Mudah)**
1. Buka **MySQL** (via phpMyAdmin atau command line)
2. Buat database kosong:
   ```sql
   CREATE DATABASE titik_temu;
   ```
3. **SELESAI!** Tabel akan otomatis dibuat saat aplikasi pertama kali running
4. Spring Boot akan auto-create tabel: `users`, `laporan`, dan `v_laporan_detail`

**Metode 2: Import Manual (OPSIONAL - Jika ingin sample data untuk testing)**
1. Buka **phpMyAdmin**
2. Pilih **Import** > **Choose File**
3. Pilih file `titiktemu-api/db_schema`
4. Klik **Go/Execute**
5. Database + tabel + sample data akan terbuat

**Sample data berguna untuk:**
- Testing tanpa harus register user baru
- Lihat contoh laporan yang sudah ada
- Login dengan user: `mahasiswa1`, password: `password123`

### 3️⃣ Konfigurasi IP Address (PENTING!)

**⚠️ WAJIB: Ganti IP Address di 3 file berikut dengan IP komputer Anda!**

Cara cek IP komputer:
```bash
# Windows (PowerShell/CMD)
ipconfig

# Linux/Mac
ifconfig
# atau
ip addr show
```

**File yang harus diubah:**

#### a. Backend API - `titiktemu-api/src/main/resources/application.properties`
```properties
# Ganti sesuai konfigurasi MySQL Anda
spring.datasource.url=jdbc:mysql://localhost:3306/titik_temu?useSSL=false&serverTimezone=Asia/Jakarta&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=         # Isi password MySQL Anda (kosongkan jika tidak ada)
```

#### b. Mobile App - `titiktemu-mobile/app/src/main/java/com/stis/titiktemu/util/Config.kt`
```kotlin
object Config {
    const val BASE_URL = "http://IP_ANDA:8080/api/"           // Ganti IP_ANDA
    const val SERVER_IP = "IP_ANDA"                            // Ganti IP_ANDA
    const val SERVER_PORT = 8080
    const val SERVER_BASE = "http://IP_ANDA:8080"             // Ganti IP_ANDA
}
```

#### c. Mobile App - `titiktemu-mobile/app/src/main/java/com/stis/titiktemu/util/Constants.kt`
```kotlin
object Constants {
    const val BASE_URL = "http://IP_ANDA:8080/api/"          // Ganti IP_ANDA
    // ... konstanta lainnya
}
```

**Contoh:**
Jika IP komputer Anda adalah `192.168.1.100`, maka:
```kotlin
const val BASE_URL = "http://192.168.1.100:8080/api/"
const val SERVER_IP = "192.168.1.100"
const val SERVER_BASE = "http://192.168.1.100:8080"
```

### 4️⃣ Install & Run Backend API

```bash
cd titiktemu-api

# Install dependencies
mvnw clean install
# Atau jika Maven sudah terinstall global:
# mvn clean install

# Run aplikasi
mvnw spring-boot:run
# Atau:
# mvn spring-boot:run
```

**Alternatif: Jalankan dari IDE**
- Buka IntelliJ IDEA / Eclipse
- Import project Maven
- Jalankan `TitikTemuApplication.java`

**Verifikasi Backend:**
- API berjalan di: `http://localhost:8080/api`
- Swagger UI: `http://localhost:8080/docs/swagger-ui`
- API Docs: `http://localhost:8080/docs/open-api`

### 5️⃣ Install & Run Mobile App

```bash
cd titiktemu-mobile

# Pastikan sudah mengubah IP di Config.kt dan Constants.kt!

# Buka dengan Android Studio
# File > Open > Pilih folder titiktemu-mobile

# Sync Gradle (otomatis atau klik Sync Now)

# Jalankan di emulator atau device fisik:
# - Klik tombol Run (▶️)
# - Pilih device/emulator
```

**Catatan:**
- Pastikan Android device dan komputer terhubung ke **jaringan WiFi yang sama**
- Jika menggunakan emulator, gunakan IP komputer (bukan `localhost` atau `10.0.2.2`)
- Minimal Android SDK 24 (Android 7.0 Nougat)

---

## 📚 Dokumentasi Lengkap

Untuk dokumentasi detail masing-masing komponen:
- **Backend API**: Lihat [titiktemu-api/README.md](titiktemu-api/README.md)
- **Mobile App**: Lihat [titiktemu-mobile/README.md](titiktemu-mobile/README.md)

---

## 🧪 Testing

### Test Backend API
```bash
cd titiktemu-api

# Run unit tests
mvnw test

# Test dengan Swagger UI
# Buka: http://localhost:8080/docs/swagger-ui
```

### Test Mobile App
```bash
cd titiktemu-mobile

# Run unit tests
./gradlew test

# Run di device
# Gunakan Android Studio Run/Debug
```

---

## 📖 Endpoint API (Ringkasan)

| Method | Endpoint | Deskripsi | Auth |
|--------|----------|-----------|------|
| POST | `/api/auth/register` | Register user baru | ❌ |
| POST | `/api/auth/login` | Login user | ❌ |
| GET | `/api/users/profile` | Get user profile | ✅ |
| PUT | `/api/users/profile` | Update profile | ✅ |
| PUT | `/api/users/change-password` | Change password | ✅ |
| DELETE | `/api/users/delete` | Delete account | ✅ |
| GET | `/api/laporan` | Get semua laporan | ✅ |
| GET | `/api/laporan/{id}` | Get laporan by ID | ✅ |
| POST | `/api/laporan` | Create laporan baru | ✅ |
| PUT | `/api/laporan/{id}` | Update laporan | ✅ |
| DELETE | `/api/laporan/{id}` | Delete laporan | ✅ |
| GET | `/api/laporan/filter` | Filter laporan | ✅ |

Dokumentasi lengkap tersedia di Swagger UI.

---

## 🔐 Keamanan

- Password di-hash menggunakan **BCrypt**
- Token menggunakan **JWT** dengan expiry 24 jam
- File upload dibatasi maksimal **10MB**
- CORS dikonfigurasi untuk development

---

## 👥 Tim Pengembang

Developed by STIS Students - Kelas K203403 (Pemrograman Platform Khusus)

---

## 📄 Lisensi

Project ini dibuat untuk keperluan akademik Politeknik Statistika STIS.

---

## 📞 Support

Untuk pertanyaan atau issue, silakan hubungi tim pengembang.

---

**Happy Coding! 🚀**

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
    <version>2.8.13</version>
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
Pemrograman Platform Khusus  

---

**Terima Kasih!**