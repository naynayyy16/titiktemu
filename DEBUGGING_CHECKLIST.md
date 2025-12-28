# Authentication Flow - Debugging Checklist

## ✅ Step-by-Step Testing Guide

### Test 1: Fresh Install (No Token)
**Steps**:
1. Uninstall app or clear app data
2. Open app
3. **EXPECTED**: Splash screen → Login screen

**Logcat Check**:
```
✅ SplashViewModel - Token check - Token value: 'null'
✅ SplashViewModel - ❌ No valid token - Navigating to Login
✅ LoginScreen appears
```

---

### Test 2: Login with Valid Credentials
**Steps**:
1. Enter username: `your_username`
2. Enter password: `your_password`
3. Click "Login"

**Logcat Check**:
```
✅ AuthRepository - 🔄 Attempting login for user: your_username
✅ AuthRepository - ✅ Login successful! Token: [jwt-token-here]
✅ AuthRepository - ✅ Token saved to preferences
✅ LoginScreen navigates to Home
```

**If you see error**:
```
❌ AuthRepository - ❌ Login failed: [error-message]
```
→ Check backend is running on `http://10.5.50.127:8080`

---

### Test 3: Access Profile (Should NOT be 403 anymore)
**Steps**:
1. From Home screen, click Profile icon
2. Wait for profile data to load

**Logcat Check**:
```
✅ OkHttp - GET /api/users/profile
✅ OkHttp - Authorization: Bearer [valid-token]
✅ Response: 200 OK (Profile loaded successfully!)
```

**If you still see 403**:
```
❌ OkHttp - <-- 403 Forbidden
```
→ Something went wrong with token saving. Check Test 2 logcat again.

---

### Test 4: App Restart with Valid Token
**Steps**:
1. Close app completely
2. Reopen app
3. **EXPECTED**: Splash screen → Home screen (skips Login!)

**Logcat Check**:
```
✅ SplashViewModel - Token check - Token value: '[valid-jwt-token]'
✅ SplashViewModel - ✅ Valid token found - Navigating to Home
✅ HomeScreen appears directly
```

---

### Test 5: Logout
**Steps**:
1. From Profile screen, click Logout
2. Confirm logout

**Logcat Check**:
```
✅ AuthRepository - 🔄 Logging out - clearing all preferences
✅ AuthRepository - ✅ All preferences cleared
✅ Navigate to Login screen
```

---

### Test 6: App Restart After Logout
**Steps**:
1. Close app completely
2. Reopen app
3. **EXPECTED**: Splash screen → Login screen (no token found)

**Logcat Check**:
```
✅ SplashViewModel - Token check - Token value: 'null'
✅ SplashViewModel - ❌ No valid token - Navigating to Login
✅ LoginScreen appears again
```

---

## 🔴 Common Issues & Fixes

### Issue 1: Still Getting 403 on Profile
**Cause**: Token not being saved
**Check**:
- Did you see "✅ Token saved to preferences" in logcat?
- Is login actually succeeding? (See Test 2 logcat)

**Fix**:
- Check PreferencesManager.kt - verify saveToken() is working
- Check AuthRepository.kt - verify it's calling saveToken()

---

### Issue 2: Login Success but Still Goes to Login on Restart
**Cause**: Token saved but SplashViewModel can't read it
**Check**:
- After login, close and reopen app
- Check SplashViewModel logcat

**Fix**:
- Verify PreferencesManager.getToken() is working correctly
- Check dataStore is properly initialized

---

### Issue 3: App Crashes During Login
**Cause**: Likely ViewModelFactory issue
**Fix**:
- Verify all ViewModel constructors accept Context parameter
- Check ViewModelFactory.kt has all ViewModels registered

---

## 📋 Quick Logcat Search

Open Android Studio Logcat and search for these to see entire auth flow:

```
AuthRepository        - Shows login/register/logout operations
SplashViewModel       - Shows token checking
LoginScreen           - Shows navigation decisions
OkHttp               - Shows actual HTTP requests with tokens
```

---

## 🎯 Success Criteria

You'll know it's fixed when:
- ✅ Fresh install → Shows Login (no token)
- ✅ Login succeeds → Shows Home
- ✅ Profile loads without 403 error
- ✅ App restart → Shows Home directly (found token)
- ✅ Logout → Clears token
- ✅ After logout + restart → Shows Login again
