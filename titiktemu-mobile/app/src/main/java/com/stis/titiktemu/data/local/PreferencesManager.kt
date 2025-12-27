package com.stis.titiktemu.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.stis.titiktemu.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "titiktemu_prefs")

class PreferencesManager(private val context: Context) {
    private val tokenKey = stringPreferencesKey(Constants.PREF_TOKEN)
    private val usernameKey = stringPreferencesKey(Constants.PREF_USERNAME)
    private val emailKey = stringPreferencesKey(Constants.PREF_EMAIL)
    private val namaLengkapKey = stringPreferencesKey(Constants.PREF_NAMA_LENGKAP)
    private val jabatanKey = stringPreferencesKey(Constants.PREF_JABATAN)
    private val nimNipKey = stringPreferencesKey(Constants.PREF_NIM_NIP)
    private val noHpKey = stringPreferencesKey(Constants.PREF_NO_HP)

    suspend fun getToken(): String? {
        return try {
            context.dataStore.data.map { prefs ->
                prefs[tokenKey]
            }.first()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[tokenKey] = token
        }
    }

    suspend fun saveUserData(
        username: String,
        email: String,
        namaLengkap: String,
        jabatan: String,
        nimNip: String?,
        noHp: String
    ) {
        context.dataStore.edit { prefs ->
            prefs[usernameKey] = username
            prefs[emailKey] = email
            prefs[namaLengkapKey] = namaLengkap
            prefs[jabatanKey] = jabatan
            nimNip?.let { prefs[nimNipKey] = it }
            prefs[noHpKey] = noHp
        }
    }

    val tokenFlow: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[tokenKey]
    }

    val usernameFlow: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[usernameKey]
    }

    suspend fun clearAll() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
