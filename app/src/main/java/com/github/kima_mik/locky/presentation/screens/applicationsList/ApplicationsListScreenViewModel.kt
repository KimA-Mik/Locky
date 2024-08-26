package com.github.kima_mik.locky.presentation.screens.applicationsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kima_mik.locky.domain.lock.useCase.LockApplicationsUseCase
import com.github.kima_mik.locky.domain.lock.useCase.SubscribeToLockStatusUseCase
import com.github.kima_mik.locky.domain.lock.useCase.UnlockApplicationsUseCase
import com.github.kima_mik.locky.domain.packages.useCase.LockPackageUseCase
import com.github.kima_mik.locky.domain.packages.useCase.SubscribeToPackageEntriesUseCase
import com.github.kima_mik.locky.domain.packages.useCase.UnlockPackageUseCase
import com.github.kima_mik.locky.presentation.common.ComposeEvent
import com.github.kima_mik.locky.presentation.screens.applicationsList.events.AppListUiEvent
import com.github.kima_mik.locky.presentation.screens.applicationsList.events.AppListUserEvent
import com.github.kima_mik.locky.presentation.screens.applicationsList.mappers.toAppEntry
import com.github.kima_mik.locky.presentation.screens.applicationsList.model.AppEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ApplicationsListScreenViewModel(
    subscribeToPackageEntries: SubscribeToPackageEntriesUseCase,
    lockStatus: SubscribeToLockStatusUseCase,
    private val lockPackage: LockPackageUseCase,
    private val unlockPackage: UnlockPackageUseCase,
    private val lockApplications: LockApplicationsUseCase,
    private val unlockApplications: UnlockApplicationsUseCase
) : ViewModel() {
    private val _outEvents = MutableStateFlow(ComposeEvent<AppListUiEvent>(null))
    val uiEvents = _outEvents.asStateFlow()

    private val packages = subscribeToPackageEntries().map { result ->
        when (result) {
            is SubscribeToPackageEntriesUseCase.Result.Loading -> ApplicationsListScreenState.Packages.Loading(
                result.progress
            )

            is SubscribeToPackageEntriesUseCase.Result.Success -> {
                val entries = result.data.map { it.toAppEntry() }
                ApplicationsListScreenState.Packages.Entries(entries)
            }
        }
    }
    private val showGrantPackageUsageStatsDialog = MutableStateFlow(false)
    private val showGrantManageOverlayDialog = MutableStateFlow(false)
    private val locked = lockStatus().onEach {
        when (it) {
            SubscribeToLockStatusUseCase.Result.ShouldRun -> lockApplications()
            SubscribeToLockStatusUseCase.Result.ShouldStop -> unlockApplications()
            else -> Unit
        }
    }.map {
        when (it) {
            SubscribeToLockStatusUseCase.Result.Running -> true
            SubscribeToLockStatusUseCase.Result.ShouldRun -> true
            SubscribeToLockStatusUseCase.Result.ShouldStop -> false
            SubscribeToLockStatusUseCase.Result.Stopped -> false
        }
    }

    val state = combine(
        packages,
        locked,
        showGrantPackageUsageStatsDialog,
        showGrantManageOverlayDialog
    ) { packages, locked, showGrantPackageUsageStatsDialog, showGrantManageOverlayDialog ->
        ApplicationsListScreenState(
            packages = packages,
            showGrantPackageUsageStatsDialog = showGrantPackageUsageStatsDialog,
            showRequireMangeOverlayDialog = showGrantManageOverlayDialog,
            locked = locked
        )
    }.flowOn(Dispatchers.Default)

    fun onEvent(event: AppListUserEvent) {
        when (event) {
            is AppListUserEvent.ApplicationToggled -> onApplicationToggled(event.entry)
            AppListUserEvent.ConfirmGrantPackageUsageStatsDialog -> onConfirmGrantPackageUsageStatsDialog()
            AppListUserEvent.DismissGrantPackageUsageStatsDialog -> onDismissGrantPackageUsageStatsDialog()
            AppListUserEvent.ConfirmGrantManageOverlayDialog -> onConfirmGrantManageOverlayDialog()
            AppListUserEvent.DismissGrantManageOverlayDialog -> onDismissGrantManageOverlayDialog()
            AppListUserEvent.LockApps -> onLockApps()
            AppListUserEvent.UnlockApps -> onUnlockApps()
        }
    }

    private fun onApplicationToggled(entry: AppEntry) = viewModelScope.launch {
        if (entry.locked) {
            unlockPackage(entry.packageName)
        } else {
            when (lockPackage(entry.packageName)) {
                LockPackageUseCase.Result.NoDataUsageStatsPermission -> {
                    showGrantPackageUsageStatsDialog.value = true
                }

                LockPackageUseCase.Result.NoManageOverlayPermission -> {
                    showGrantManageOverlayDialog.value = true
                }

                LockPackageUseCase.Result.Success -> {}
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

    private fun onLockApps() = viewModelScope.launch {
        lockApplications()
    }

    private fun onUnlockApps() = viewModelScope.launch {
        unlockApplications()
    }
}