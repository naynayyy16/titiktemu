package com.stis.titiktemu.util

sealed class Resource<T> {
    class Idle<T> : Resource<T>()           // Ready but not started
    class Loading<T> : Resource<T>()        // Action in progress
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val message: String) : Resource<T>()
}
