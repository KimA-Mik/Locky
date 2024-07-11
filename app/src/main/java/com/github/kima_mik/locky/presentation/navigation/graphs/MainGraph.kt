package com.github.kima_mik.locky.presentation.navigation.graphs

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.github.kima_mik.locky.presentation.screens.applicationsList.ApplicationsListScreen
import com.github.kima_mik.locky.presentation.screens.applicationsList.ApplicationsListScreenState
import com.github.kima_mik.locky.presentation.screens.applicationsList.ApplicationsListScreenViewModel
import com.github.kima_mik.locky.presentation.screens.applicationsList.events.AppListUserEvent
import com.github.kima_mik.locky.presentation.screens.lockscreen.LockScreen
import com.github.kima_mik.locky.presentation.screens.lockscreen.LockScreenState
import com.github.kima_mik.locky.presentation.screens.lockscreen.LockScreenViewModel
import com.github.kima_mik.locky.presentation.screens.lockscreen.event.OnLockScreenEvent
import org.koin.androidx.compose.koinViewModel

private const val MAIN_GRAPH_ROUTE = "main"
private const val LOCK_ROUTE = "lock"
private const val APP_LIST_ROUTE = "app_list"

fun NavGraphBuilder.mainGraph(
    navController: NavController,
    snackbarHostState: SnackbarHostState
) =
    navigation(startDestination = LOCK_ROUTE, route = MAIN_GRAPH_ROUTE) {
        composable(LOCK_ROUTE) {
            val viewModel: LockScreenViewModel = koinViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle(initialValue = LockScreenState())
            val uiEvent by viewModel.uiEvents.collectAsStateWithLifecycle()
            val onEvent = remember<OnLockScreenEvent> {
                {
                    viewModel.onEvent(it)
                }
            }

            LockScreen(
                state = state,
                snackbarHostState = snackbarHostState,
                navController = navController,
                uiEvent = uiEvent,
                onEvent = onEvent
            )
        }

        composable(APP_LIST_ROUTE) {
            val viewModel: ApplicationsListScreenViewModel = koinViewModel()
            val state by
            viewModel.state.collectAsStateWithLifecycle(initialValue = ApplicationsListScreenState())
            val onEvent = remember<(AppListUserEvent) -> Unit> {
                {
                    viewModel.onEvent(it)
                }
            }

            val uiEvents = viewModel.uiEvents.collectAsStateWithLifecycle()

            ApplicationsListScreen(
                state = state,
                uiEvents = uiEvents,
                onEvent = onEvent
            )
        }
    }

fun NavController.enterApp() {
    this.popBackStack()
    this.navigate(APP_LIST_ROUTE) {
        launchSingleTop = true
    }
}