package com.stis.titiktemu.ui.screens.create

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stis.titiktemu.data.api.UnauthorizedException
import com.stis.titiktemu.data.model.Laporan
import com.stis.titiktemu.data.repository.LaporanRepository
import com.stis.titiktemu.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateViewModel(context: Context) : ViewModel() {
    private val laporanRepository = LaporanRepository(context)

    private val _createState = MutableStateFlow<Resource<Laporan>>(Resource.Idle())
    val createState = _createState.asStateFlow()
    
    private val _navigateToLogin = MutableSharedFlow<Unit>()
    val navigateToLogin = _navigateToLogin.asSharedFlow()

    fun createLaporan(
        tipe: String,
        judul: String,
        deskripsi: String,
        kategori: String,
        lokasi: String,
        tanggalKejadian: String,
        imageUri: Uri? = null
    ) {
        _createState.value = Resource.Loading()
        viewModelScope.launch {
            try {
                val result = laporanRepository.createLaporan(
                    tipe, judul, deskripsi, kategori, lokasi, tanggalKejadian, imageUri
                )
                _createState.value = result
            } catch (e: UnauthorizedException) {
                _createState.value = Resource.Error("Session expired")
                _navigateToLogin.emit(Unit)
            } catch (e: Exception) {
                _createState.value = Resource.Error(e.message ?: "Error")
            }
        }
    }
}
