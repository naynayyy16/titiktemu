# Authentication Flow Diagram

## ❌ BEFORE (BROKEN)
```
┌─────────────────────────────────────┐
│ AuthViewModel                        │
│ _loginState = Resource.Success("")   │ ← WRONG! Always success
└─────────────────────────────────────┘
                ↓
┌─────────────────────────────────────┐
│ LoginScreen LaunchedEffect          │
│                                     │
│ if (loginState is Success) {        │ ← Always true!
│   navigate to Home ❌               │
│ }                                   │
└─────────────────────────────────────┘
                ↓
        🏠 HOME SCREEN SHOWN
                ↓
     ❌ User not actually logged in
        (No token saved)
                ↓
    🔒 Profile returns 403 Forbidden
        (No Authorization header)
```

---

## ✅ AFTER (FIXED)
```
┌─────────────────────────────────────┐
│ AuthViewModel                        │
│ _loginState = Resource.Loading()    │ ← CORRECT! Start loading
└─────────────────────────────────────┘
                ↓
┌─────────────────────────────────────┐
│ LoginScreen shows                   │
│ (no premature navigation)           │
│                                     │
│ User enters credentials...          │
└─────────────────────────────────────┘
                ↓
┌─────────────────────────────────────┐
│ viewModel.login(user, pass)         │
│ calls AuthRepository.login()        │
└─────────────────────────────────────┘
                ↓
┌─────────────────────────────────────┐
│ Backend returns JWT token           │
│ + AuthResponse object               │
└─────────────────────────────────────┘
                ↓
┌─────────────────────────────────────┐
│ AuthRepository                      │
│ - Saves token to PreferencesManager │
│ - Returns Resource.Success(token)   │
└─────────────────────────────────────┘
                ↓
┌─────────────────────────────────────┐
│ LoginScreen LaunchedEffect          │
│                                     │
│ if (loginState is Success &&        │ ← NEW! Check non-empty
│     data.isNotEmpty()) {            │
│   navigate to Home ✅               │
│ }                                   │
└─────────────────────────────────────┘
                ↓
        🏠 HOME SCREEN SHOWN
                ↓
     ✅ User actually logged in
        (Token in PreferencesManager)
                ↓
      AuthInterceptor adds:
      Authorization: Bearer [token]
                ↓
    🔓 Profile returns 200 OK!
        (Has valid Authorization header)
```

---

## Token Life Cycle

### Fresh Install
```
Splash Screen
    ↓
SplashViewModel.checkTokenAndNavigate()
    ↓
PreferencesManager.getToken() → null
    ↓
Navigate to Login Screen ✅
```

### After Successful Login
```
Login Screen
    ↓
User submits credentials
    ↓
AuthRepository.login()
    ↓
Backend returns token
    ↓
PreferencesManager.saveToken(token) ✅
    ↓
Navigate to Home Screen ✅
    ↓
All API calls include: Authorization: Bearer [token]
```

### App Restart (Token Exists)
```
Splash Screen
    ↓
SplashViewModel.checkTokenAndNavigate()
    ↓
PreferencesManager.getToken() → [valid-token]
    ↓
Token is not null and not empty ✅
    ↓
Navigate to Home Screen ✅
    ↓
AuthInterceptor uses saved token
```

### After Logout
```
Profile Screen
    ↓
User clicks Logout
    ↓
AuthRepository.logout()
    ↓
PreferencesManager.clearAll() ✅
    ↓
Navigate to Login Screen ✅
    ↓
Next app restart → goes to Login (no token)
```

---

## What Changed in Code

### AuthViewModel.kt
```diff
- private val _loginState = MutableStateFlow<Resource<String>>(Resource.Success(""))
+ private val _loginState = MutableStateFlow<Resource<String>>(Resource.Loading())
```

### LoginScreen.kt
```diff
  LaunchedEffect(loginState) {
-     if (loginState is Resource.Success) {
-         onNavigateToHome()
-     }
+     if (loginState is Resource.Success && (loginState as Resource.Success).data.isNotEmpty()) {
+         onNavigateToHome()
+     }
  }
```

### RegisterScreen.kt
```diff
  LaunchedEffect(loginState) {
-     if (loginState is Resource.Success) {
-         onNavigateToHome()
-     }
+     if (loginState is Resource.Success && (loginState as Resource.Success).data.isNotEmpty()) {
+         onNavigateToHome()
+     }
  }
```

---

## Why This Fix Works

**Root Cause**: 
- Initial state was `Success("")` - Kotlin treats this as a successful result
- LaunchedEffect checked `is Resource.Success` - this was ALWAYS true initially
- User got sent to Home without actual login

**Solution**:
- Changed initial state to `Loading()` - nothing navigates during loading
- Added `.data.isNotEmpty()` check - only navigates when actual token received
- Token is a non-empty JWT string like `"eyJhbGciOiJIUzI1NiIs..."`

**Result**:
- Screen stays on Login until actual backend response
- Token is properly saved before navigation
- Profile and other protected endpoints work (have valid token)
