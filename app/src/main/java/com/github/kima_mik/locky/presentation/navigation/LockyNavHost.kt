package com.github.kima_mik.locky.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.github.kima_mik.locky.presentation.navigation.graphs.mainGraph

@Composable
fun LockyNavHost(
    navHostController: NavHostController, expanded: Boolean,
    snackbarHostState: SnackbarHostState
) =
    NavHost(navHostController, startDestination = "main") {
        mainGraph(
            navController = navHostController,
            snackbarHostState = snackbarHostState
        )
    }