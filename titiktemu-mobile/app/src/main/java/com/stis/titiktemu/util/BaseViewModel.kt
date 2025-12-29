package com.stis.titiktemu.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stis.titiktemu.data.api.UnauthorizedException
import com.stis.titiktemu.data.local.PreferencesManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel(private val context: Context) : ViewModel() {
    private val preferencesManager = PreferencesManager(context)
    
    private val _logoutEvent = MutableSharedFlow<Unit>()
    val logoutEvent = _logoutEvent.asSharedFlow()

    protected suspend fun <T> handleApiCall(apiCall: suspend () -> Resource<T>): Resource<T> {
        return try {
            apiCall()
        } catch (e: UnauthorizedException) {
            // Token expired - force logout
            preferencesManager.clearAll()
            viewModelScope.launch {
                _logoutEvent.emit(Unit)
            }
            Resource.Error("Session expired. Please login again.")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }
}
