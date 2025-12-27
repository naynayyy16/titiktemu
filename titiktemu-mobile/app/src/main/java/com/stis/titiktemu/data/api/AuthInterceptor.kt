package com.stis.titiktemu.data.api

import android.util.Log
import com.stis.titiktemu.data.local.PreferencesManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val preferencesManager: PreferencesManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            val originalRequest = chain.request()

            // Add token to request if available
            val token = try {
                runBlocking {
                    preferencesManager.getToken()
                }
            } catch (e: Exception) {
                Log.e("AuthInterceptor", "Error getting token", e)
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

            // Handle 401 Unauthorized
            if (response.code == 401) {
                Log.w("AuthInterceptor", "Unauthorized response")
            }

            response
        } catch (e: Exception) {
            Log.e("AuthInterceptor", "Interceptor error", e)
            throw e
        }
    }
}
