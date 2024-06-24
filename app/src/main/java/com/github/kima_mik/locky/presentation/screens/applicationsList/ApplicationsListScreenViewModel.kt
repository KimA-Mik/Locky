package com.github.kima_mik.locky.presentation.screens.applicationsList

import androidx.lifecycle.ViewModel
import com.github.kima_mik.locky.domain.packages.useCase.GetInstalledPackagesUseCase
import com.github.kima_mik.locky.presentation.screens.applicationsList.mappers.toAppEntry
import com.github.kima_mik.locky.presentation.screens.applicationsList.model.AppEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class ApplicationsListScreenViewModel(
    getInstalledPackages: GetInstalledPackagesUseCase
) : ViewModel() {
    private val packages = MutableStateFlow(emptyList<AppEntry>())
    private val temp = MutableStateFlow("")

    init {
        packages.value = getInstalledPackages().map { it.toAppEntry() }
    }

    val state = combine(
        packages,
        temp
    ) { packages, temp ->
        ApplicationsListScreenState(packages)
    }


}