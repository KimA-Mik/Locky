package com.github.kima_mik.locky.presentation.navigation.graphs

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.github.kima_mik.locky.presentation.screens.applicationsList.ApplicationsListScreen
import com.github.kima_mik.locky.presentation.screens.applicationsList.ApplicationsListScreenState
import com.github.kima_mik.locky.presentation.screens.applicationsList.ApplicationsListScreenViewModel
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.mainGraph() = navigation(startDestination = "root", route = "main") {
    composable("root") {
        val viewModel: ApplicationsListScreenViewModel = koinViewModel()
        val state by
        viewModel.state.collectAsStateWithLifecycle(initialValue = ApplicationsListScreenState())

        ApplicationsListScreen(
            state = state
        )
    }
}