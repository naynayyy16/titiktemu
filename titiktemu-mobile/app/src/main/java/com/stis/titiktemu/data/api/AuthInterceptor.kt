package com.stis.titiktemu.data.api

import com.stis.titiktemu.data.local.PreferencesManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val preferencesManager: PreferencesManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Add token to request if available
        val token = runBlocking {
            preferencesManager.getToken()
        }
        val newRequest = if (token != null) {
            originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        } else {
            originalRequest
        }

        var response = chain.proceed(newRequest)

        // Handle 401 Unauthorized - clearAll is handled asynchronously
        if (response.code == 401) {
            // clearAll() is a suspend function and cannot be called from intercept
            // The app should handle this in the repository or view model layer
        }

        return response
    }
}
