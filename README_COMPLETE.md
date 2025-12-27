# Titik Temu - Lost & Found Platform

Aplikasi platform untuk melaporkan barang hilang dan barang yang ditemukan di kampus STIS.

## 📁 Project Structure

```
titiktemu/
├── titiktemu-api/          # Backend Spring Boot REST API ✅
│   ├── src/
│   ├── pom.xml
│   ├── mvnw & mvnw.cmd
│   └── db_schema/
│
└── titiktemu-mobile/       # Frontend Android App (BARU) ✅
    ├── app/
    ├── build.gradle.kts
    ├── settings.gradle.kts
    ├── gradle.properties
    └── README.md
```

## 🎯 Project Overview

### Backend API (titiktemu-api)
- Spring Boot REST API
- PostgreSQL Database
- Authentication & Authorization
- CRUD Operations untuk Laporan
- User Management

**Base URL**: `http://10.236.221.58:8080/api/`

### Mobile App (titiktemu-mobile)
- Android Kotlin + Jetpack Compose
- MVVM + Repository Pattern
- Retrofit untuk API Integration
- DataStore untuk local storage
- Material 3 Design System

## 🚀 Features

### Authentication
- ✅ Login
- ✅ Register
- ✅ Logout
- ✅ Token Management (Auto-refresh, 401 handling)

### Laporan Management
- ✅ List laporan (Hilang/Ditemukan)
- ✅ Search & Filter
- ✅ Detail view
- ✅ Create laporan
- ✅ Edit laporan
- ✅ Delete laporan
- ✅ Status tracking

### User Profile
- ✅ View profile
- ✅ Edit profile
- ✅ Change password
- ✅ Delete account

## 💻 Tech Stack

### Backend
- Java 17
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT Authentication

### Frontend
- Kotlin
- Jetpack Compose
- Retrofit 2
- DataStore Preferences
- Coroutines
- Material 3

## 📱 Getting Started

### Backend
```bash
cd titiktemu-api
mvn clean install
mvn spring-boot:run
```

Server runs on: `http://localhost:8080`

### Mobile App
```bash
cd titiktemu-mobile
# Open in Android Studio & Run
```

## 🔌 API Endpoints

### Authentication
- `POST /auth/register` - Register user
- `POST /auth/login` - Login

### Users
- `GET /users/profile` - Get user profile
- `PUT /users/profile` - Update profile
- `PUT /users/change-password` - Change password
- `DELETE /users/account` - Delete account

### Laporan
- `GET /laporan` - List all laporan (with filters)
- `GET /laporan/{id}` - Get detail laporan
- `POST /laporan` - Create laporan
- `PUT /laporan/{id}` - Update laporan
- `DELETE /laporan/{id}` - Delete laporan

## 🎨 Design

### Color Scheme
- **Primary**: #2563EB (Blue)
- **Success**: #10B981 (Green)
- **Danger**: #EF4444 (Red)

### UI/UX
- Minimalist design
- Card-based layout
- Smooth animations
- Intuitive navigation

## 📊 Data Models

### Laporan
```kotlin
{
    id: Long,
    tipe: String (HILANG/TEMUKAN),
    judul: String,
    deskripsi: String,
    kategori: String,
    lokasi: String,
    tanggalKejadian: String,
    status: String (AKTIF/SELESAI),
    fotoUrl: String?,
    pelaporNama: String,
    pelaporJabatan: String,
    pelaporNoHp: String,
    pelaporEmail: String,
    createdAt: String,
    updatedAt: String
}
```

### User
```kotlin
{
    id: Long,
    username: String,
    email: String,
    namaLengkap: String,
    jabatan: String,
    nimNip: String?,
    noHp: String
}
```

## 🔐 Security

- ✅ JWT Token Authentication
- ✅ Encrypted DataStore Storage
- ✅ Cleartext traffic restricted (only for dev IP)
- ✅ Password validation
- ✅ Email validation

## 📚 Documentation

- [Backend API Documentation](titiktemu-api/README.md)
- [Mobile App Documentation](titiktemu-mobile/README.md)

## 🧪 Testing

### Backend
```bash
cd titiktemu-api
mvn test
```

### Mobile
```bash
cd titiktemu-mobile
./gradlew test  # Unit tests
./gradlew connectedAndroidTest  # Instrumented tests
```

## 📝 Development Notes

### Adding New Features

1. **Backend**: Add endpoint in controller, update service/repository
2. **Mobile**: Add screen, ViewModel, Repository, and Model

### Code Style
- Kotlin: Use camelCase for functions, PascalCase for classes
- Compose: Keep components small and reusable
- Java: Follow Spring conventions

## 🐛 Known Issues

None at this time. Please report bugs via [GitHub Issues](https://github.com/stis/titiktemu/issues)

## 📞 Contact

- **Project Owner**: STIS Development Team
- **Email**: dev@stis.ac.id

## 📄 License

© 2024 STIS. All rights reserved.

---

**Project Status**: ✅ Production Ready

**Last Updated**: December 27, 2024

**Version**: 1.0.0
