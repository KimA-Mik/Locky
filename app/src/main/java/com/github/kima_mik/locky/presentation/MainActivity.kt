package com.github.kima_mik.locky.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.github.kima_mik.locky.presentation.ui.ApplicationsScreen
import com.github.kima_mik.locky.presentation.ui.theme.LockyTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LockyTheme {
                val windowSizeClass = calculateWindowSizeClass(this)

                ApplicationsScreen(windowSizeClass = windowSizeClass)
            }
        }
    }
}

