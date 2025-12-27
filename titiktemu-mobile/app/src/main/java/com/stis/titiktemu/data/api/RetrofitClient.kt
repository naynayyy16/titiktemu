package com.stis.titiktemu.data.api

import android.content.Context
import com.stis.titiktemu.data.local.PreferencesManager
import com.stis.titiktemu.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
        val preferencesManager = PreferencesManager(context)
        
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(preferencesManager))
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
