package com.stis.titiktemu.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.stis.titiktemu.ui.screens.auth.LoginScreen
import com.stis.titiktemu.ui.screens.auth.RegisterScreen
import com.stis.titiktemu.ui.screens.create.CreateLaporanScreen
import com.stis.titiktemu.ui.screens.detail.DetailLaporanScreen
import com.stis.titiktemu.ui.screens.edit.EditLaporanScreen
import com.stis.titiktemu.ui.screens.home.HomeScreen
import com.stis.titiktemu.ui.screens.profile.ChangePasswordScreen
import com.stis.titiktemu.ui.screens.profile.EditProfileScreen
import com.stis.titiktemu.ui.screens.profile.ProfileScreen
import com.stis.titiktemu.ui.screens.splash.SplashScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Splash.route
) {
    NavHost(navController = navController, startDestination = startDestination) {
        // Splash
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // Authentication
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        // Home
        composable(Screen.Home.route) {
            HomeScreen(
                onDetailClick = { laporanId ->
                    navController.navigate(Screen.DetailLaporan.createRoute(laporanId))
                },
                onCreateClick = {
                    navController.navigate(Screen.CreateLaporan.route)
                },
                onProfileClick = {
                    navController.navigate(Screen.Profile.route)
                },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // Laporan Detail
        composable(
            Screen.DetailLaporan.route,
            arguments = listOf(
                navArgument("laporanId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val laporanId = backStackEntry.arguments?.getLong("laporanId") ?: return@composable
            DetailLaporanScreen(
                laporanId = laporanId,
                onBack = { navController.popBackStack() },
                onEdit = { navController.navigate(Screen.EditLaporan.createRoute(laporanId)) },
                onDelete = { navController.popBackStack() }
            )
        }

        // Create Laporan
        composable(Screen.CreateLaporan.route) {
            CreateLaporanScreen(
                onBack = { navController.popBackStack() },
                onSuccess = {
                    navController.popBackStack()
                }
            )
        }

        // Edit Laporan
        composable(
            Screen.EditLaporan.route,
            arguments = listOf(
                navArgument("laporanId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val laporanId = backStackEntry.arguments?.getLong("laporanId") ?: return@composable
            EditLaporanScreen(
                laporanId = laporanId,
                onBack = { navController.popBackStack() },
                onSuccess = { navController.popBackStack() }
            )
        }

        // Profile
        composable(Screen.Profile.route) {
            ProfileScreen(
                onBack = { navController.popBackStack() },
                onEditProfile = { navController.navigate(Screen.EditProfile.route) },
                onChangePassword = { navController.navigate(Screen.ChangePassword.route) },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // Edit Profile
        composable(Screen.EditProfile.route) {
            EditProfileScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // Change Password
        composable(Screen.ChangePassword.route) {
            ChangePasswordScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
