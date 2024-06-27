package com.github.kima_mik.locky.presentation.screens.applicationsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kima_mik.locky.domain.packages.useCase.LockPackageUseCase
import com.github.kima_mik.locky.domain.packages.useCase.SubscribeToPackageEntriesUseCase
import com.github.kima_mik.locky.domain.packages.useCase.UnlockPackageUseCase
import com.github.kima_mik.locky.presentation.screens.applicationsList.events.AppListUserEvent
import com.github.kima_mik.locky.presentation.screens.applicationsList.mappers.toAppEntry
import com.github.kima_mik.locky.presentation.screens.applicationsList.model.AppEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ApplicationsListScreenViewModel(
    subscribeToPackageEntries: SubscribeToPackageEntriesUseCase,
    private val lockPackage: LockPackageUseCase,
    private val unlockPackage: UnlockPackageUseCase
) : ViewModel() {
    private val packages: Flow<List<AppEntry>> = subscribeToPackageEntries().map { packs ->
        packs.map {
            it.toAppEntry()
        }
    }
    private val temp = MutableStateFlow("")

    val state = combine(
        packages,
        temp
    ) { packages, temp ->
        ApplicationsListScreenState(packages)
    }

    fun onEvent(event: AppListUserEvent) {
        when (event) {
            is AppListUserEvent.ApplicationToggled -> onApplicationToggled(event.entry)
        }
    }

    private fun onApplicationToggled(entry: AppEntry) = viewModelScope.launch {
        if (entry.locked) {
            unlockPackage(entry.packageName)
        } else {
            lockPackage(entry.packageName)
        }
    }
}