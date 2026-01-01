# Titik Temu Mobile - Android App

Aplikasi Android mobile untuk platform Lost & Found "Titik Temu" menggunakan **Kotlin** dengan **Jetpack Compose** UI framework.

---

## 📱 Deskripsi

Titik Temu Mobile adalah aplikasi Android native yang menyediakan interface untuk berinteraksi dengan Titik Temu API. Aplikasi ini memungkinkan sivitas akademika STIS untuk melaporkan barang hilang, mengumumkan barang yang ditemukan, dan mencari barang yang hilang dengan mudah melalui smartphone.

---

## ✨ Fitur Utama

- ✅ **Authentication**: Register & Login dengan token persistence
- ✅ **Home Screen**: List laporan hilang & ditemukan dengan pull-to-refresh
- ✅ **Search & Filter**: Filter berdasarkan tipe dan kategori
- ✅ **Detail Laporan**: View detail lengkap dengan info kontak pelapor
- ✅ **Create Laporan**: Form create laporan baru dengan upload foto
- ✅ **Edit Laporan**: Edit laporan milik sendiri
- ✅ **Delete Laporan**: Hapus laporan dengan konfirmasi
- ✅ **Profile Management**: View & edit profil user
- ✅ **Change Password**: Ganti password dengan validasi
- ✅ **Logout**: Clear session & kembali ke welcome screen
- ✅ **WhatsApp Integration**: Contact pelapor via WhatsApp
- ✅ **Auto Token Handling**: Automatic JWT token refresh & logout on expire
- ✅ **Offline Persistence**: Simpan user data dengan encrypted DataStore

---

## 🏗️ Architecture

Aplikasi ini menggunakan arsitektur **MVVM (Model-View-ViewModel)** dengan **Repository Pattern**:

```
┌─────────────────────────────────────────┐
│         UI Layer (Jetpack Compose)      │
│  ┌──────────┐  ┌──────────┐  ┌────────┐│
│  │ Screens  │  │Components│  │ Theme  ││
│  └──────────┘  └──────────┘  └────────┘│
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│         ViewModel Layer                 │
│  ┌────────────────────────────────────┐ │
│  │  State Management & Business Logic │ │
│  └────────────────────────────────────┘ │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│         Repository Layer                │
│  ┌──────────────┐  ┌─────────────────┐ │
│  │ API Gateway  │  │ Local Storage   │ │
│  └──────────────┘  └─────────────────┘ │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│         Data Layer                      │
│  ┌──────────┐  ┌──────────┐            │
│  │ Retrofit │  │DataStore │            │
│  └──────────┘  └──────────┘            │
└─────────────────────────────────────────┘
```

### Package Structure
- **data**: Models, API interfaces, Repository implementations
- **ui**: Compose screens, components, navigation, theme
- **util**: Helper classes, constants, extensions

---

## 🛠️ Tech Stack

| Category | Technology |
|----------|-----------|
| **Language** | Kotlin |
| **UI Framework** | Jetpack Compose (Material 3) |
| **Architecture** | MVVM + Repository Pattern |
| **Dependency Injection** | Manual (Factory Pattern) |
| **Networking** | Retrofit 2.9.0 + OkHttp |
| **Serialization** | Gson |
| **Async** | Kotlin Coroutines + Flow |
| **Local Storage** | DataStore (Preferences) |
| **Image Loading** | Coil (Compose) |
| **Navigation** | Jetpack Navigation Compose |
| **Build Tool** | Gradle (Kotlin DSL) |
| **Min SDK** | 24 (Android 7.0 Nougat) |
| **Target SDK** | 34 (Android 14) |

### Key Dependencies
```kotlin
// Compose UI
implementation("androidx.compose.ui:ui:1.5.4")
implementation("androidx.compose.material3:material3:1.1.2")
implementation("androidx.navigation:navigation-compose:2.7.5")

// Networking
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")

// Storage
implementation("androidx.datastore:datastore-preferences:1.0.0")

// Image Loading
implementation("io.coil-kt:coil-compose:2.5.0")

// Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
```

---

## 📁 Project Structure

```
titiktemu-mobile/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/stis/titiktemu/
│   │       │   ├── data/
│   │       │   │   ├── api/
│   │       │   │   │   ├── ApiService.kt              # Retrofit API interface
│   │       │   │   │   └── AuthInterceptor.kt         # JWT token interceptor
│   │       │   │   ├── model/
│   │       │   │   │   ├── User.kt                    # User data class
│   │       │   │   │   ├── Laporan.kt                 # Laporan data class
│   │       │   │   │   ├── Requests.kt                # Request DTOs
│   │       │   │   │   └── Responses.kt               # Response DTOs
│   │       │   │   └── repository/
│   │       │   │       ├── UserRepository.kt          # User data operations
│   │       │   │       └── LaporanRepository.kt       # Laporan data operations
│   │       │   ├── ui/
│   │       │   │   ├── screens/
│   │       │   │   │   ├── welcome/
│   │       │   │   │   │   └── WelcomeScreen.kt       # Landing screen
│   │       │   │   │   ├── auth/
│   │       │   │   │   │   ├── LoginScreen.kt         # Login form
│   │       │   │   │   │   ├── LoginViewModel.kt
│   │       │   │   │   │   ├── RegisterScreen.kt      # Register form
│   │       │   │   │   │   └── RegisterViewModel.kt
│   │       │   │   │   ├── home/
│   │       │   │   │   │   ├── HomeScreen.kt          # Main list laporan
│   │       │   │   │   │   └── HomeViewModel.kt
│   │       │   │   │   ├── detail/
│   │       │   │   │   │   ├── DetailLaporanScreen.kt # Detail view
│   │       │   │   │   │   └── DetailViewModel.kt
│   │       │   │   │   ├── create/
│   │       │   │   │   │   ├── CreateLaporanScreen.kt # Create form
│   │       │   │   │   │   └── CreateViewModel.kt
│   │       │   │   │   ├── edit/
│   │       │   │   │   │   ├── EditLaporanScreen.kt   # Edit form
│   │       │   │   │   │   └── EditViewModel.kt
│   │       │   │   │   ├── profile/
│   │       │   │   │   │   ├── ProfileScreen.kt       # User profile
│   │       │   │   │   │   ├── ProfileViewModel.kt
│   │       │   │   │   │   ├── EditProfileScreen.kt
│   │       │   │   │   │   ├── EditProfileViewModel.kt
│   │       │   │   │   │   ├── ChangePasswordScreen.kt
│   │       │   │   │   │   └── ChangePasswordViewModel.kt
│   │       │   │   │   └── ViewModelFactory.kt        # ViewModel factory
│   │       │   │   ├── components/
│   │       │   │   │   ├── LaporanCard.kt             # Reusable laporan card
│   │       │   │   │   ├── CustomButton.kt            # Custom button
│   │       │   │   │   └── LoadingIndicator.kt        # Loading UI
│   │       │   │   ├── theme/
│   │       │   │   │   ├── Color.kt                   # Color scheme
│   │       │   │   │   ├── Theme.kt                   # Material theme
│   │       │   │   │   └── Type.kt                    # Typography
│   │       │   │   └── navigation/
│   │       │   │       └── NavGraph.kt                # Navigation routes
│   │       │   ├── util/
│   │       │   │   ├── Constants.kt                   # App constants
│   │       │   │   ├── Config.kt                      # Server config (IP)
│   │       │   │   ├── Resource.kt                    # Result wrapper
│   │       │   │   ├── BaseViewModel.kt               # Base ViewModel
│   │       │   │   └── Extensions.kt                  # Kotlin extensions
│   │       │   ├── MainActivity.kt                    # Single activity
│   │       │   └── TitikTemuApp.kt                    # Main app composable
│   │       ├── res/
│   │       │   ├── drawable/                          # Images & icons
│   │       │   ├── mipmap/                            # App icons
│   │       │   └── values/
│   │       │       ├── strings.xml                    # String resources
│   │       │       ├── colors.xml                     # Color resources
│   │       │       └── themes.xml                     # Android themes
│   │       └── AndroidManifest.xml                    # App manifest
│   └── build.gradle.kts                               # App-level build config
├── build.gradle.kts                                   # Project-level build config
├── settings.gradle.kts                                # Gradle settings
└── README.md                                          # File ini
```

---

## 🚀 Instalasi & Setup

### Prasyarat
- **Android Studio** Hedgehog (2023.1.1) atau lebih baru
- **JDK 17** (biasanya bundled dengan Android Studio)
- **Android SDK 34** (Android 14)
- **Device/Emulator** dengan minimal Android 7.0 (API 24)
- **Backend API** harus sudah running (lihat [titiktemu-api/README.md](../titiktemu-api/README.md) untuk setup)
- **MySQL Database** dengan database `titik_temu` sudah dibuat (auto-create atau import)

### 1️⃣ Clone Repository

Jika belum clone:
```bash
git clone <repository-url>
cd titiktemu/titiktemu-mobile
```

### 2️⃣ Konfigurasi IP Address (SANGAT PENTING!)

**⚠️ WAJIB: Ganti IP Address dengan IP komputer server API Anda!**

Cara cek IP komputer server:
```bash
# Windows (PowerShell/CMD)
ipconfig

# Linux/Mac
ifconfig
# atau
ip addr show
```

**File yang harus diubah:**

#### File 1: `app/src/main/java/com/stis/titiktemu/util/Config.kt`
```kotlin
package com.stis.titiktemu.util

object Config {
    const val BASE_URL = "http://IP_ANDA:8080/api/"        // GANTI IP_ANDA!
    const val SERVER_IP = "IP_ANDA"                         // GANTI IP_ANDA!
    const val SERVER_PORT = 8080
    const val SERVER_BASE = "http://IP_ANDA:8080"          // GANTI IP_ANDA!
}
```

#### File 2: `app/src/main/java/com/stis/titiktemu/util/Constants.kt`
```kotlin
package com.stis.titiktemu.util

object Constants {
    const val BASE_URL = "http://IP_ANDA:8080/api/"        // GANTI IP_ANDA!
    const val PREF_TOKEN = "token"
    const val PREF_USERNAME = "username"
    // ... konstanta lainnya
}
```

**Contoh:**
Jika IP komputer server adalah `192.168.1.100`:
```kotlin
const val BASE_URL = "http://192.168.1.100:8080/api/"
const val SERVER_IP = "192.168.1.100"
const val SERVER_BASE = "http://192.168.1.100:8080"
```

**⚠️ CATATAN PENTING:**
- **JANGAN gunakan `localhost` atau `127.0.0.1`** - tidak akan berfungsi!
- **JANGAN gunakan `10.0.2.2`** kecuali di emulator Android Studio (tidak untuk device fisik)
- Pastikan **device dan server di jaringan WiFi yang sama**
- Gunakan IP **LAN** komputer server (biasanya 192.168.x.x atau 10.x.x.x)

### 3️⃣ Buka Project di Android Studio

1. Buka **Android Studio**
2. Klik **File** → **Open**
3. Navigate ke folder `titiktemu-mobile`
4. Klik **OK**
5. Tunggu Gradle sync selesai (bisa beberapa menit pertama kali)

Jika muncul error "SDK not found":
- **File** → **Project Structure** → **SDK Location**
- Set Android SDK path (biasanya `C:\Users\<user>\AppData\Local\Android\Sdk`)

### 4️⃣ Sync Gradle Dependencies

Jika belum otomatis, sync manual:
- Klik **File** → **Sync Project with Gradle Files**
- Atau klik icon **Sync** di toolbar
- Tunggu sampai selesai download dependencies

### 5️⃣ Setup Device/Emulator

**Opsi A: Menggunakan Emulator**
1. Klik **Device Manager** (icon HP di toolbar)
2. Klik **Create Device**
3. Pilih **Pixel 4** atau device lainnya
4. Pilih **System Image**: API 34 (Android 14)
5. Download jika belum ada
6. Klik **Finish**
7. Start emulator

**Opsi B: Menggunakan Device Fisik**
1. Enable **Developer Options** di HP:
   - Settings → About Phone → Tap "Build Number" 7x
2. Enable **USB Debugging**:
   - Settings → Developer Options → USB Debugging: ON
3. Sambungkan HP ke komputer via USB
4. Izinkan USB debugging di HP
5. Device akan muncul di Android Studio

### 6️⃣ Run Application

1. Pastikan **Backend API sudah running** di komputer server
2. Pastikan **IP sudah diganti** di Config.kt dan Constants.kt
3. Pilih device/emulator di toolbar Android Studio
4. Klik tombol **Run** (▶️) atau tekan **Shift + F10**
5. Tunggu build & install selesai
6. Aplikasi akan otomatis terbuka di device

**First Run:**
- Welcome Screen → Register → Login → Home

---

## 📱 User Flow

```
Welcome Screen
    ├─→ Login ──────────┐
    └─→ Register ───────┤
                        ▼
                   Home Screen (List Laporan)
                        │
        ┌───────────────┼───────────────┐
        ▼               ▼               ▼
   Detail Laporan   Profile        Create Laporan
        │               │
    Edit/Delete    Edit Profile
                   Change Password
                   Delete Account
                        │
                    Logout ──→ Welcome Screen
```

---

## 🎨 UI Screens

### 1. Welcome Screen
- Logo aplikasi
- Tombol Login & Register
- Intro singkat tentang aplikasi

### 2. Login Screen
- Input: Username & Password
- Button: Login
- Link: "Belum punya akun? Register"

### 3. Register Screen
- Input: Username, Email, Password, Nama Lengkap, Jabatan, NIM/NIP, No HP
- Validasi form realtime
- Button: Register
- Link: "Sudah punya akun? Login"

### 4. Home Screen
- **Top Bar**: Logo + Search icon + Profile icon
- **Filter Chips**: Semua / Hilang / Temukan
- **Kategori Chips**: Semua kategori / specific
- **List Laporan**: Cards dengan foto, judul, kategori, lokasi
- **FAB**: Tombol Create Laporan (+)
- **Pull-to-Refresh**: Swipe down untuk refresh

### 5. Detail Laporan Screen
- Foto barang (fullwidth)
- Info lengkap: Tipe, Judul, Kategori, Lokasi, Tanggal, Deskripsi
- Info Pelapor: Nama, Jabatan, No HP
- Button: Contact via WhatsApp
- Jika laporan milik sendiri: Button Edit & Delete

### 6. Create/Edit Laporan Screen
- Form: Tipe, Judul, Kategori, Lokasi, Tanggal Kejadian, Deskripsi
- Upload Foto (optional)
- Button: Simpan / Update

### 7. Profile Screen
- Avatar placeholder
- Info user: Nama, Email, Username, Jabatan, NIM/NIP, No HP
- Button: Edit Profile, Change Password, Logout, Delete Account

### 8. Edit Profile Screen
- Form: Nama Lengkap, Jabatan, NIM/NIP, No HP
- Button: Simpan

### 9. Change Password Screen
- Input: Old Password, New Password, Confirm Password
- Validasi password match
- Button: Change Password

---

## 🔄 State Management

Menggunakan **Kotlin StateFlow** untuk reactive state:

```kotlin
// ViewModel
private val _uiState = MutableStateFlow(UiState())
val uiState: StateFlow<UiState> = _uiState.asStateFlow()

// Screen (Compose)
val state by viewModel.uiState.collectAsState()

when (state) {
    is Resource.Loading -> LoadingIndicator()
    is Resource.Success -> SuccessContent(data)
    is Resource.Error -> ErrorMessage(error)
}
```

---

## 🌐 API Integration

### Retrofit Setup

**ApiService.kt:**
```kotlin
interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse
    
    @GET("laporan")
    suspend fun getLaporan(): List<Laporan>
    
    // ... endpoints lainnya
}
```

**AuthInterceptor.kt:**
Automatically menambahkan JWT token ke setiap request:
```kotlin
class AuthInterceptor(private val tokenProvider: () -> String?) : Interceptor {
    override fun intercept(chain: Chain): Response {
        val token = tokenProvider()
        val request = if (token != null) {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else chain.request()
        
        return chain.proceed(request)
    }
}
```

### Repository Pattern

**LaporanRepository.kt:**
```kotlin
class LaporanRepository(
    private val api: ApiService,
    private val context: Context
) {
    suspend fun getLaporan(): Resource<List<Laporan>> {
        return try {
            val response = api.getLaporan()
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }
}
```

---

## 💾 Local Storage (DataStore)

Menyimpan user session & preferences:

```kotlin
// Save token
dataStore.edit { preferences ->
    preferences[stringPreferencesKey("token")] = token
}

// Read token
val token = dataStore.data.map { preferences ->
    preferences[stringPreferencesKey("token")]
}.first()

// Clear all data (logout)
dataStore.edit { it.clear() }
```

**Data yang disimpan:**
- JWT Token
- Username
- Email
- Nama Lengkap
- Jabatan
- NIM/NIP
- No HP

---

## 🧪 Testing

### Manual Testing
1. Register user baru
2. Login dengan user yang baru dibuat
3. View list laporan di Home
4. Filter laporan by tipe & kategori
5. View detail laporan
6. Create laporan baru (dengan/tanpa foto)
7. Edit laporan milik sendiri
8. Delete laporan milik sendiri
9. View & edit profile
10. Change password
11. Logout & login kembali

### Unit Testing
```bash
# Run unit tests
./gradlew test

# Run dengan coverage
./gradlew testDebugUnitTestCoverage
```

---

## 🔧 Troubleshooting

### 1. Error: Unable to connect to API

**Penyebab:**
- Backend API tidak running
- IP address salah
- Device & server tidak di jaringan yang sama
- Firewall blocking port 8080

**Solusi:**
```bash
# 1. Pastikan API running
cd titiktemu-api
mvnw spring-boot:run

# 2. Cek IP server
ipconfig  # Windows
ifconfig  # Linux/Mac

# 3. Pastikan device connected ke WiFi yang sama

# 4. Test koneksi dari browser HP
http://IP_SERVER:8080/api

# 5. Disable firewall sementara atau allow port 8080
```

### 2. Gradle Sync Failed

**Solusi:**
```bash
# 1. File → Invalidate Caches → Invalidate and Restart

# 2. Hapus .gradle folder
rm -rf .gradle/

# 3. Sync ulang
./gradlew --refresh-dependencies
```

### 3. App Crash on Launch

**Cek Logcat:**
- **View → Tool Windows → Logcat**
- Filter by "Error" atau "Exception"
- Cari stack trace

**Common Issues:**
- Missing permissions di AndroidManifest.xml
- IP tidak valid di Config.kt
- Network security configuration (HTTP not allowed)

### 4. 401 Unauthorized Error

**Penyebab:**
- Token expired
- Token tidak valid
- Token tidak dikirim ke server

**Solusi:**
- Logout & login ulang
- Check AuthInterceptor implementation
- Clear app data & login ulang

### 5. Emulator Very Slow

**Solusi:**
- Enable hardware acceleration (Intel HAXM / AMD)
- Allocate more RAM to emulator
- Gunakan device fisik (faster)

---

## 📦 Build APK

### Debug APK (Testing)
```bash
./gradlew assembleDebug
# Output: app/build/outputs/apk/debug/app-debug.apk
```

### Release APK (Production)
```bash
# 1. Generate keystore (first time only)
keytool -genkey -v -keystore titiktemu.keystore -alias titiktemu -keyalg RSA -keysize 2048 -validity 10000

# 2. Update app/build.gradle.kts
signingConfigs {
    create("release") {
        storeFile = file("../titiktemu.keystore")
        storePassword = "your-password"
        keyAlias = "titiktemu"
        keyPassword = "your-password"
    }
}

# 3. Build release APK
./gradlew assembleRelease
# Output: app/build/outputs/apk/release/app-release.apk
```

---

## 🎯 Best Practices Implemented

✅ **MVVM Architecture** - Separation of concerns  
✅ **Repository Pattern** - Single source of truth  
✅ **Dependency Injection** - Manual via Factory (lightweight)  
✅ **Coroutines** - Async operations  
✅ **StateFlow** - Reactive UI updates  
✅ **Sealed Classes** - Type-safe result handling  
✅ **Data Classes** - Immutable models  
✅ **Extension Functions** - Code reusability  
✅ **String Resources** - Externalized strings  
✅ **Material 3 Design** - Modern UI/UX  
✅ **Error Handling** - Global exception handling  
✅ **Loading States** - User feedback  
✅ **Pull-to-Refresh** - Data freshness  

---

## 🔐 Security Considerations

- ✅ Token stored in encrypted DataStore (NOT SharedPreferences)
- ✅ Password tidak pernah disimpan di local
- ✅ HTTPS recommended for production
- ✅ Input validation untuk semua form
- ✅ Auto-logout on token expiry
- ⚠️ Currently using HTTP (change to HTTPS for production)

---

## 📝 Notes

- **Minimum Android version**: Android 7.0 (API 24)
- **Target Android version**: Android 14 (API 34)
- **Screen orientation**: Portrait only (bisa diubah di Manifest)
- **Dark mode**: Not yet implemented (future enhancement)
- **Offline mode**: Login persistence only (no offline CRUD)
- **File upload**: Implemented (multipart/form-data)
- **Image caching**: Yes (Coil library)
- **Pagination**: Not yet implemented (load all data)

---

## 🚀 Future Enhancements

Potential improvements:
- [ ] Dark mode support
- [ ] Push notifications (Firebase Cloud Messaging)
- [ ] Offline mode with Room database
- [ ] Pagination for large datasets
- [ ] Image compression before upload
- [ ] Camera integration (take photo directly)
- [ ] Multi-language support (i18n)
- [ ] Unit & UI tests
- [ ] Biometric authentication
- [ ] Share laporan via social media

---

## 🔗 Resources

- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Material Design 3](https://m3.material.io/)
- [Retrofit](https://square.github.io/retrofit/)
- [Coil](https://coil-kt.github.io/coil/)
- [Coroutines Guide](https://kotlinlang.org/docs/coroutines-guide.html)

---

## 👨‍💻 Development Tips

### Hot Reload
- Compose mendukung live preview di Android Studio
- **Build → Build & Refresh** untuk update preview
- Gunakan `@Preview` annotation untuk component preview

### Debugging
- Gunakan `Log.d()` untuk logging
- **Logcat** filter by package name: `com.stis.titiktemu`
- **Layout Inspector** untuk debug UI hierarchy

### Performance
- Avoid recomposition - gunakan `remember` & `derivedStateOf`
- LazyColumn untuk list optimization
- Image caching dengan Coil

---

**Developed with ❤️ using Jetpack Compose**

**STIS Academic Community - 2025**

│   │       │   │   ├── repository/
│   │       │   │   └── local/
│   │       │   ├── ui/
│   │       │   │   ├── theme/
│   │       │   │   ├── components/
│   │       │   │   ├── screens/
│   │       │   │   └── navigation/
│   │       │   └── util/
│   │       ├── res/
│   │       │   └── ...
│   │       └── AndroidManifest.xml
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

## 🎨 Design System

### Colors
- **Primary**: #2563EB (Blue)
- **Success**: #10B981 (Green) - untuk "Ditemukan"
- **Danger**: #EF4444 (Red) - untuk "Hilang"
- **Background**: #F9FAFB (Light Gray)

### Typography
- **Display**: 28sp Bold
- **Headline**: 24sp SemiBold
- **Title**: 20sp SemiBold
- **Body**: 16sp Regular
- **Label**: 14sp Medium
- **Caption**: 12sp Regular

### Components
- **LaporanCard**: Card dengan informasi laporan
- **CustomButton**: Material 3 button dengan loading state
- **CustomTextField**: TextField dengan error handling
- **StatusBadge**: Badge untuk status laporan
- **KategoriChip**: Chip untuk kategori

## 🔌 API Integration

### Base URL
```
http://10.236.221.58:8080/api/
```

### Features
- ✅ **Token Management**: Token disimpan di DataStore
- ✅ **Auto-Interceptor**: Menambahkan token ke setiap request
- ✅ **401 Handling**: Auto-logout jika token expired
- ✅ **HTTP Logging**: Debug logging untuk development

## 🚀 Getting Started

### Prerequisites
- Android Studio Giraffe atau terbaru
- JDK 17 atau terbaru
- Android SDK 34 (API Level 34)
- Minimum SDK: API Level 24

### Installation

1. **Clone Project**
```bash
cd titiktemu-mobile
```

2. **Open di Android Studio**
   - File → Open → Select `titiktemu-mobile` folder

3. **Sync Gradle**
   - Build → Rebuild Project

4. **Run Aplikasi**
   - Run → Run 'app' (atau tekan Shift+F10)
   - Pilih emulator atau physical device

## 📝 Screens

### 1. SplashScreen
- Cek token validity
- Navigate ke Home atau Login
- Duration: 2 detik

### 2. LoginScreen
- Form: Username, Password
- Link ke Register
- Loading & error states

### 3. RegisterScreen
- Form: Username, Email, Password, Nama Lengkap, Jabatan, NIM/NIP, No HP
- Dropdown: Jabatan (Mahasiswa, Dosen, Tendik, Cleaning Service, Lainnya)
- Validasi field

### 4. HomeScreen
- Tab: Semua, Hilang, Ditemukan
- Search & Filter
- List laporan dalam card
- Pull-to-refresh
- FAB untuk create laporan

### 5. DetailLaporanScreen
- Laporan details
- Badge: Type, Kategori, Status
- Contact info & action buttons
- Edit/Delete (jika owner)

### 6. CreateLaporanScreen
- Form: Tipe, Kategori, Judul, Deskripsi, Lokasi, Tanggal
- Validasi semua field
- Submit button

### 7. EditLaporanScreen
- Pre-filled data
- Tambahan status field
- Update button

### 8. ProfileScreen
- Avatar dengan inisial
- User info
- Menu: Edit Profil, Ganti Password, Keluar, Hapus Akun

### 9. EditProfileScreen
- Edit: Nama, Jabatan, NIM/NIP, No HP, Email
- Save button

### 10. ChangePasswordScreen
- Form: Old Password, New Password, Confirm Password
- Validasi & change button

## 🔐 Security

- ✅ **Cleartext Traffic**: Hanya allow untuk IP 10.236.221.58
- ✅ **Token Encryption**: Token disimpan di encrypted DataStore
- ✅ **HTTPS Ready**: Siap untuk production HTTPS

## 🧪 Testing

```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

## 📚 Dependencies

```
androidx:
  - compose-bom: 2024.01.00
  - compose-ui, material3
  - navigation-compose: 2.7.6
  - lifecycle-viewmodel-compose: 2.7.0
  - datastore-preferences: 1.0.0

networking:
  - retrofit2: 2.9.0
  - okhttp3: 4.12.0
  - gson: 2.9.0

async:
  - kotlinx-coroutines: 1.7.3

image:
  - coil: 2.5.0
```

## 📦 Build & APK

### Build APK
```bash
./gradlew assembleRelease
```

APK akan tersimpan di:
```
app/build/outputs/apk/release/app-release.apk
```

## 🐛 Troubleshooting

### API tidak terhubung
- Check internet connection
- Verify Base URL di `Constants.kt`
- Check network_security_config.xml

### Token tidak tersimpan
- Cek DataStore permissions di AndroidManifest.xml
- Verify PreferencesManager implementation

### Gradle sync error
- Delete `.gradle` folder
- Run: `./gradlew clean build`

## 📞 Support

Untuk bantuan atau bug reports, hubungi tim development.

## 📄 License

© 2024 Titik Temu - STIS. All rights reserved.

---

**Status**: ✅ Production Ready

**Last Updated**: December 2024
