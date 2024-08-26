package com.github.kima_mik.locky.presentation.screens.applicationsList

import com.github.kima_mik.locky.presentation.screens.applicationsList.model.AppEntry

data class ApplicationsListScreenState(
    val packages: Packages = Packages.Loading(0f),
    val dialogs: Dialogs = Dialogs(),
    val locked: Boolean = false,
) {
    sealed interface Packages {
        data class Loading(val progress: Float) : Packages
        data class Entries(val data: List<AppEntry>) : Packages
    }

    data class Dialogs(
        val showRequirePackageUsageStatsDialog: Boolean = false,
        val showRequireMangeOverlayDialog: Boolean = false,
        val showRequireNotificationPermissionDialog: Boolean = false
    )
}