package com.stis.titiktemu.ui.screens.detail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stis.titiktemu.data.model.Laporan
import com.stis.titiktemu.data.repository.LaporanRepository
import com.stis.titiktemu.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(context: Context) : ViewModel() {
    private val laporanRepository = LaporanRepository(context)

    private val _laporanState = MutableStateFlow<Resource<Laporan>>(Resource.Loading())
    val laporanState = _laporanState.asStateFlow()

    private val _deleteState = MutableStateFlow<Resource<String>>(Resource.Idle())
    val deleteState = _deleteState.asStateFlow()

    fun loadLaporanDetail(id: Long) {
        _laporanState.value = Resource.Loading()
        viewModelScope.launch {
            val result = laporanRepository.getLaporanDetail(id)
            _laporanState.value = result
        }
    }

    fun deleteLaporan(id: Long) {
        _deleteState.value = Resource.Loading()
        viewModelScope.launch {
            val result = laporanRepository.deleteLaporan(id)
            _deleteState.value = when (result) {
                is Resource.Success -> Resource.Success("Deleted")
                is Resource.Error -> Resource.Error(result.message)
                is Resource.Loading -> Resource.Loading()
                is Resource.Idle -> Resource.Idle()
            }
        }
    }
}
