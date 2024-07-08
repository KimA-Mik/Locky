package com.github.kima_mik.locky.presentation.screens.applicationsList.events

import com.github.kima_mik.locky.presentation.screens.applicationsList.model.AppEntry

sealed interface AppListUserEvent {
    data class ApplicationToggled(val entry: AppEntry) : AppListUserEvent
    data object ConfirmGrantPackageUsageStatsDialog:AppListUserEvent
    data object DismissGrantPackageUsageStatsDialog:AppListUserEvent
}