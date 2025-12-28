package com.stis.titiktemu.ui.screens.create

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stis.titiktemu.data.model.Laporan
import com.stis.titiktemu.data.repository.LaporanRepository
import com.stis.titiktemu.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateViewModel(context: Context) : ViewModel() {
    private val laporanRepository = LaporanRepository(context)

    private val _createState = MutableStateFlow<Resource<Laporan>>(Resource.Idle())
    val createState = _createState.asStateFlow()

    fun createLaporan(
        tipe: String,
        judul: String,
        deskripsi: String,
        kategori: String,
        lokasi: String,
        tanggalKejadian: String
    ) {
        _createState.value = Resource.Loading()
        viewModelScope.launch {
            val result = laporanRepository.createLaporan(
                tipe, judul, deskripsi, kategori, lokasi, tanggalKejadian
            )
            _createState.value = result
        }
    }
}
