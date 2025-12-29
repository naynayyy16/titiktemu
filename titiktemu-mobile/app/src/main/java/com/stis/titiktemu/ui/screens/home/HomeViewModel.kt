package com.stis.titiktemu.ui.screens.home

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

class HomeViewModel(context: Context) : ViewModel() {
    private val laporanRepository = LaporanRepository(context)

    private val _laporanState = MutableStateFlow<Resource<List<Laporan>>>(Resource.Loading())
    val laporanState = _laporanState.asStateFlow()

    private val _selectedFilter = MutableStateFlow<String?>(null)
    val selectedFilter = _selectedFilter.asStateFlow()
    
    private val _navigateToLogin = MutableSharedFlow<Unit>()
    val navigateToLogin = _navigateToLogin.asSharedFlow()

    init {
        loadLaporan()
    }

    fun loadLaporan(
        tipe: String? = null,
        kategori: String? = null,
        status: String? = null,
        lokasi: String? = null,
        search: String? = null
    ) {
        _laporanState.value = Resource.Loading()
        viewModelScope.launch {
            try {
                val result = laporanRepository.getLaporan(tipe, kategori, status, lokasi, search)
                _laporanState.value = result
            } catch (e: UnauthorizedException) {
                _laporanState.value = Resource.Error("Session expired")
                _navigateToLogin.emit(Unit)
            } catch (e: Exception) {
                _laporanState.value = Resource.Error(e.message ?: "Error loading data")
            }
        }
    }

    fun filterByTipe(tipe: String?) {
        _selectedFilter.value = tipe
        loadLaporan(tipe = tipe)
    }
}
