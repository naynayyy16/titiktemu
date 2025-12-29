package com.stis.titiktemu.ui.screens.detail

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

class DetailViewModel(context: Context) : ViewModel() {
    private val laporanRepository = LaporanRepository(context)

    private val _laporanState = MutableStateFlow<Resource<Laporan>>(Resource.Loading())
    val laporanState = _laporanState.asStateFlow()

    private val _deleteState = MutableStateFlow<Resource<String>>(Resource.Idle())
    val deleteState = _deleteState.asStateFlow()
    
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

    fun deleteLaporan(id: Long) {
        _deleteState.value = Resource.Loading()
        viewModelScope.launch {
            try {
                val result = laporanRepository.deleteLaporan(id)
                _deleteState.value = when (result) {
                    is Resource.Success -> Resource.Success("Deleted")
                    is Resource.Error -> {
                        _errorMessage.emit(result.message ?: "Gagal menghapus laporan")
                        Resource.Error(result.message)
                    }
                    is Resource.Loading -> Resource.Loading()
                    is Resource.Idle -> Resource.Idle()
                }
            } catch (e: UnauthorizedException) {
                _deleteState.value = Resource.Error("Session expired")
                _navigateToLogin.emit(Unit)
            } catch (e: Exception) {
                val errorMsg = e.message ?: "Error"
                _errorMessage.emit(errorMsg)
                _deleteState.value = Resource.Error(errorMsg)
            }
        }
    }
}
