package com.github.kima_mik.locky.presentation.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.github.kima_mik.locky.presentation.navigation.LockyNavHost
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun ApplicationsListScreen(windowSizeClass: WindowSizeClass) {
    KoinAndroidContext {
        val navController = rememberNavController()
        val expanded = windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact

        LockyNavHost(
            navHostController = navController,
            expanded = expanded
        )
    }
}

