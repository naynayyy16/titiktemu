package com.stis.titiktemu

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.stis.titiktemu.ui.navigation.NavGraph
import com.stis.titiktemu.ui.theme.TitikTemuTheme

@Composable
fun TitikTemuApp() {
    Log.d("TitikTemuApp", "App started")
    TitikTemuTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            NavGraph(navController)
        }
    }
}
