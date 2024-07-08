package com.github.kima_mik.locky.presentation.screens.applicationsList

import com.github.kima_mik.locky.presentation.screens.applicationsList.model.AppEntry

data class ApplicationsListScreenState(
    val packages: List<AppEntry> = emptyList(),
    val showGrantPackageUsageStatsDialog: Boolean = false,
)
