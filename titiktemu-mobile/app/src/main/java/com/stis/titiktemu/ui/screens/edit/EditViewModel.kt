package com.stis.titiktemu.ui.screens.edit

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stis.titiktemu.data.model.Laporan
import com.stis.titiktemu.data.repository.LaporanRepository
import com.stis.titiktemu.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditViewModel(context: Context) : ViewModel() {
    private val laporanRepository = LaporanRepository(context)

    private val _laporanState = MutableStateFlow<Resource<Laporan>>(Resource.Loading())
    val laporanState = _laporanState.asStateFlow()

    private val _updateState = MutableStateFlow<Resource<Laporan>>(Resource.Idle())
    val updateState = _updateState.asStateFlow()

    fun loadLaporanDetail(id: Long) {
        _laporanState.value = Resource.Loading()
        viewModelScope.launch {
            val result = laporanRepository.getLaporanDetail(id)
            _laporanState.value = result
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
            val result = laporanRepository.updateLaporan(
                id, judul, deskripsi, kategori, lokasi, tanggalKejadian, status
            )
            _updateState.value = result
        }
    }
}
