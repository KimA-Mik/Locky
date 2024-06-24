package com.github.kima_mik.locky.presentation.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.github.kima_mik.locky.presentation.screens.applicationsList.ApplicationsListScreen

fun NavGraphBuilder.mainGraph() = navigation(startDestination = "root", route = "main") {
    composable("root") {
        ApplicationsListScreen()
    }
}