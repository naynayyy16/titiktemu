package com.stis.titiktemu.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object DetailLaporan : Screen("detail/{laporanId}") {
        fun createRoute(laporanId: Long) = "detail/$laporanId"
    }
    object CreateLaporan : Screen("create")
    object EditLaporan : Screen("edit/{laporanId}") {
        fun createRoute(laporanId: Long) = "edit/$laporanId"
    }
    object Profile : Screen("profile")
    object EditProfile : Screen("edit_profile")
    object ChangePassword : Screen("change_password")
}
