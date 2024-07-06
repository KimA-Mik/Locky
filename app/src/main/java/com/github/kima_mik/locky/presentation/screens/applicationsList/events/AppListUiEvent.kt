package com.github.kima_mik.locky.presentation.screens.applicationsList.events

sealed interface AppListUiEvent {
    data object RequirePackageUsageStats: AppListUiEvent
}