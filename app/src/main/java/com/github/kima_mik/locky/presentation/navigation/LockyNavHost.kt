package com.github.kima_mik.locky.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.github.kima_mik.locky.presentation.navigation.graphs.mainGraph

@Composable
fun LockyNavHost(navHostController: NavHostController, expanded: Boolean) =
    NavHost(navHostController, startDestination = "main") {
        mainGraph()
    }