package com.stis.titiktemu

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "MainActivity created")
        try {
            setContent {
                TitikTemuApp()
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Error setting content", e)
            e.printStackTrace()
        }
    }
}
