# Titik Temu Mobile - Android App

Aplikasi Android mobile untuk platform Lost & Found "Titik Temu" menggunakan **Kotlin** dengan **Jetpack Compose** UI framework.

## 📱 Features

- ✅ **Authentication**: Login & Register dengan API Backend
- ✅ **Home Screen**: List laporan hilang & ditemukan dengan filter
- ✅ **Detail Screen**: Lihat detail laporan lengkap dengan info pelapor
- ✅ **Create Laporan**: Buat laporan baru (hilang/ditemukan)
- ✅ **Edit Laporan**: Edit laporan yang sudah dibuat
- ✅ **Profile Management**: Lihat dan edit profil user
- ✅ **Change Password**: Ganti password user
- ✅ **Token Management**: Automatic token handling via interceptor
- ✅ **Offline Storage**: DataStore untuk penyimpanan lokal

## 🏗️ Architecture

Menggunakan **MVVM + Repository Pattern**:
```
UI (Compose) → ViewModel → Repository → API/Local Storage
```

### Layer Structure
- **Data Layer**: API Integration, Repository, Local Storage
- **UI Layer**: Screens, Components, Theme
- **ViewModel**: State management & business logic

## 🛠️ Tech Stack

- **Kotlin**: Programming Language
- **Jetpack Compose**: Modern UI Framework
- **Retrofit**: REST API Client
- **DataStore**: Encrypted local storage
- **Coroutines**: Async programming
- **Material 3**: Design System

## 📁 Project Structure

```
titiktemu-mobile/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/stis/titiktemu/
│   │       │   ├── MainActivity.kt
│   │       │   ├── TitikTemuApp.kt
│   │       │   ├── data/
│   │       │   │   ├── api/
│   │       │   │   ├── model/
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
