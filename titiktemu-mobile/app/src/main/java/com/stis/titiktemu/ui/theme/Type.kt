package com.stis.titiktemu.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.stis.titiktemu.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val InterFontFamily = FontFamily(
    Font(googleFont = GoogleFont("Inter"), fontProvider = provider, weight = FontWeight.Normal),
    Font(googleFont = GoogleFont("Inter"), fontProvider = provider, weight = FontWeight.Medium),
    Font(googleFont = GoogleFont("Inter"), fontProvider = provider, weight = FontWeight.SemiBold),
    Font(googleFont = GoogleFont("Inter"), fontProvider = provider, weight = FontWeight.Bold)
)

val Typography = Typography(
    displayMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
    ),
    headlineMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
    ),
    titleMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        color = TextPrimary
    ),
    bodyMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        color = TextPrimary
    ),
    bodySmall = TextStyle(
        fontFamily = InterFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        color = TextSecondary
    ),
    labelMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = TextPrimary
    ),
    labelSmall = TextStyle(
        fontFamily = InterFontFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        color = TextTertiary
    )
)
