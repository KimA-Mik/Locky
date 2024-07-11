package com.github.kima_mik.locky.presentation.screens.applicationsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kima_mik.locky.domain.packages.useCase.LockPackageUseCase
import com.github.kima_mik.locky.domain.packages.useCase.SubscribeToPackageEntriesUseCase
import com.github.kima_mik.locky.domain.packages.useCase.UnlockPackageUseCase
import com.github.kima_mik.locky.presentation.common.ComposeEvent
import com.github.kima_mik.locky.presentation.screens.applicationsList.events.AppListUiEvent
import com.github.kima_mik.locky.presentation.screens.applicationsList.events.AppListUserEvent
import com.github.kima_mik.locky.presentation.screens.applicationsList.mappers.toAppEntry
import com.github.kima_mik.locky.presentation.screens.applicationsList.model.AppEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ApplicationsListScreenViewModel(
    subscribeToPackageEntries: SubscribeToPackageEntriesUseCase,
    private val lockPackage: LockPackageUseCase,
    private val unlockPackage: UnlockPackageUseCase
) : ViewModel() {
    private val _outEvents = MutableStateFlow(ComposeEvent<AppListUiEvent>(null))
    val uiEvents = _outEvents.asStateFlow()

    private val packages: Flow<List<AppEntry>> = subscribeToPackageEntries().map { packs ->
        packs.map {
            it.toAppEntry()
        }
    }
    private val showGrantPackageUsageStatsDialog = MutableStateFlow(false)
    private val showGrantManageOverlayDialog = MutableStateFlow(false)
    val state = combine(
        packages,
        showGrantPackageUsageStatsDialog,
        showGrantManageOverlayDialog
    ) { packages, showGrantPackageUsageStatsDialog, showGrantManageOverlayDialog ->
        ApplicationsListScreenState(
            packages = packages,
            showGrantPackageUsageStatsDialog = showGrantPackageUsageStatsDialog,
            showRequireMangeOverlayDialog = showGrantManageOverlayDialog
        )
    }.flowOn(Dispatchers.Default)

    fun onEvent(event: AppListUserEvent) {
        when (event) {
            is AppListUserEvent.ApplicationToggled -> onApplicationToggled(event.entry)
            AppListUserEvent.ConfirmGrantPackageUsageStatsDialog -> onConfirmGrantPackageUsageStatsDialog()
            AppListUserEvent.DismissGrantPackageUsageStatsDialog -> onDismissGrantPackageUsageStatsDialog()
            AppListUserEvent.ConfirmGrantManageOverlayDialog -> onConfirmGrantManageOverlayDialog()
            AppListUserEvent.DismissGrantManageOverlayDialog -> onDismissGrantManageOverlayDialog()
        }
    }

    private fun onApplicationToggled(entry: AppEntry) = viewModelScope.launch {
        if (entry.locked) {
            unlockPackage(entry.packageName)
        } else {
            val res = lockPackage(entry.packageName)
            if (res == LockPackageUseCase.Result.NoPermission) {
                showGrantPackageUsageStatsDialog.value = true
            }
        }
    }

    private fun onConfirmGrantPackageUsageStatsDialog() {
        _outEvents.value = ComposeEvent(AppListUiEvent.RequirePackageUsageStats)
        showGrantPackageUsageStatsDialog.value = false
    }

    private fun onDismissGrantPackageUsageStatsDialog() {
        showGrantPackageUsageStatsDialog.value = false
    }

    private fun onConfirmGrantManageOverlayDialog() {
        _outEvents.value = ComposeEvent(AppListUiEvent.RequireMangeOverlay)
        showGrantManageOverlayDialog.value = false
    }

    private fun onDismissGrantManageOverlayDialog() {
        showGrantManageOverlayDialog.value = true
    }
}