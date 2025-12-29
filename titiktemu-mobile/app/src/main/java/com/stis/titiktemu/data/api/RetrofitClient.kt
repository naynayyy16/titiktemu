package com.stis.titiktemu.data.api

import android.content.Context
import android.util.Log
import com.stis.titiktemu.data.local.PreferencesManager
import com.stis.titiktemu.util.Config
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private var authApi: AuthApi? = null
    private var userApi: UserApi? = null
    private var laporanApi: LaporanApi? = null

    fun getAuthApi(context: Context): AuthApi {
        if (authApi == null) {
            authApi = createRetrofit(context).create(AuthApi::class.java)
        }
        return authApi!!
    }

    fun getUserApi(context: Context): UserApi {
        if (userApi == null) {
            userApi = createRetrofit(context).create(UserApi::class.java)
        }
        return userApi!!
    }

    fun getLaporanApi(context: Context): LaporanApi {
        if (laporanApi == null) {
            laporanApi = createRetrofit(context).create(LaporanApi::class.java)
        }
        return laporanApi!!
    }

    private fun createRetrofit(context: Context): Retrofit {
        try {
            val preferencesManager = PreferencesManager(context)
            
            val loggingInterceptor = HttpLoggingInterceptor { message ->
                Log.d("OkHttp", message)
            }.apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val httpClient = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(preferencesManager))
                .addInterceptor(loggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

            Log.d("RetrofitClient", "Creating Retrofit with base URL: ${Config.BASE_URL}")

            return Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        } catch (e: Exception) {
            Log.e("RetrofitClient", "Error creating Retrofit", e)
            throw e
        }
    }
}
