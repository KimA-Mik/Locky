package com.github.kima_mik.locky.presentation.screens.applicationsList

import androidx.lifecycle.ViewModel
import com.github.kima_mik.locky.domain.packages.useCase.SubscribeToPackageEntriesUseCase
import com.github.kima_mik.locky.presentation.screens.applicationsList.mappers.toAppEntry
import com.github.kima_mik.locky.presentation.screens.applicationsList.model.AppEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class ApplicationsListScreenViewModel(
    subscribeToPackageEntries: SubscribeToPackageEntriesUseCase
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


}