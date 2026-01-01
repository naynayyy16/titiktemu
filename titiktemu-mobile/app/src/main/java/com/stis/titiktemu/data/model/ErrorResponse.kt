package com.stis.titiktemu.data.model

// Data class for parsing error response from backend
data class ErrorResponse(
    val message: String?,
    val error: String?,
    val status: Int?
)
