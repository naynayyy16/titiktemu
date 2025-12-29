package com.stis.titiktemu.ui.screens.edit

import android.content.Context
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

class EditViewModel(context: Context) : ViewModel() {
    private val laporanRepository = LaporanRepository(context)

    private val _laporanState = MutableStateFlow<Resource<Laporan>>(Resource.Loading())
    val laporanState = _laporanState.asStateFlow()

    private val _updateState = MutableStateFlow<Resource<Laporan>>(Resource.Idle())
    val updateState = _updateState.asStateFlow()
    
    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage = _errorMessage.asSharedFlow()
    
    private val _navigateToLogin = MutableSharedFlow<Unit>()
    val navigateToLogin = _navigateToLogin.asSharedFlow()

    fun loadLaporanDetail(id: Long) {
        _laporanState.value = Resource.Loading()
        viewModelScope.launch {
            try {
                val result = laporanRepository.getLaporanDetail(id)
                _laporanState.value = result
            } catch (e: UnauthorizedException) {
                _laporanState.value = Resource.Error("Session expired")
                _navigateToLogin.emit(Unit)
            } catch (e: Exception) {
                _laporanState.value = Resource.Error(e.message ?: "Error loading data")
            }
        }
    }

    fun updateLaporan(
        id: Long,
        judul: String? = null,
        deskripsi: String? = null,
        kategori: String? = null,
        lokasi: String? = null,
        tanggalKejadian: String? = null,
        status: String? = null
    ) {
        _updateState.value = Resource.Loading()
        viewModelScope.launch {
            try {
                val result = laporanRepository.updateLaporan(
                    id, judul, deskripsi, kategori, lokasi, tanggalKejadian, status
                )
                when (result) {
                    is Resource.Success -> _updateState.value = result
                    is Resource.Error -> {
                        val errorMsg = result.message ?: "Gagal mengupdate laporan"
                        _errorMessage.emit(errorMsg)
                        _updateState.value = Resource.Error(errorMsg)
                    }
                    else -> _updateState.value = result
                }
            } catch (e: UnauthorizedException) {
                _updateState.value = Resource.Error("Session expired")
                _navigateToLogin.emit(Unit)
            } catch (e: Exception) {
                val errorMsg = e.message ?: "Error"
                _errorMessage.emit(errorMsg)
                _updateState.value = Resource.Error(errorMsg)
            }
        }
    }
}
