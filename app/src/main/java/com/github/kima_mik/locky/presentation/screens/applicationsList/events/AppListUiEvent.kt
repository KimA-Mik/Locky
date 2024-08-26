package com.github.kima_mik.locky.presentation.screens.applicationsList.events

import androidx.annotation.StringRes

sealed interface AppListUiEvent {
    data object RequirePackageUsageStats: AppListUiEvent
    data object RequireMangeOverlay : AppListUiEvent
    data object RequireNotificationPermission : AppListUiEvent
    data class ShowSnackBar(@StringRes val message: Int) : AppListUiEvent
}