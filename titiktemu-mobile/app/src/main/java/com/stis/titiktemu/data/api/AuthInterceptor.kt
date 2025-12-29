package com.stis.titiktemu.data.api

import android.util.Log
import com.stis.titiktemu.data.local.PreferencesManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class UnauthorizedException(message: String) : IOException(message)

class AuthInterceptor(private val preferencesManager: PreferencesManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            val originalRequest = chain.request()
            
            // Skip auth for login/register endpoints
            val path = originalRequest.url.encodedPath
            val isAuthEndpoint = path.contains("/auth/login") || path.contains("/auth/register")

            // Add token to request if available (except for auth endpoints)
            val token = if (!isAuthEndpoint) {
                try {
                    runBlocking {
                        preferencesManager.getToken()
                    }
                } catch (e: Exception) {
                    Log.e("AuthInterceptor", "Error getting token", e)
                    null
                }
            } else {
                null
            }

            val newRequest = if (token != null) {
                originalRequest.newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()
            } else {
                originalRequest
            }

            var response = chain.proceed(newRequest)

            // Handle 401/403 Unauthorized - Clear token and force logout
            // Only for authenticated endpoints (not login/register)
            if (!isAuthEndpoint && (response.code == 401 || response.code == 403)) {
                Log.w("AuthInterceptor", "Token expired or unauthorized (${response.code})")
                runBlocking {
                    preferencesManager.clearAll()
                }
                throw UnauthorizedException("Session expired. Please login again.")
            }

            response
        } catch (e: Exception) {
            Log.e("AuthInterceptor", "Interceptor error", e)
            throw e
        }
    }
}
