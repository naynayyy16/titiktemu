package com.stis.titiktemu.util

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.dp

val AbsoluteElevation = compositionLocalOf { 0.dp }

fun String.isValidEmail(): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$".toRegex()
    return this.matches(emailRegex)
}

fun String.isValidPassword(): Boolean {
    return this.length >= 6
}

fun String.isValidPhone(): Boolean {
    val phoneRegex = "^(\\+62|0)[0-9]{9,12}$".toRegex()
    return this.matches(phoneRegex)
}
