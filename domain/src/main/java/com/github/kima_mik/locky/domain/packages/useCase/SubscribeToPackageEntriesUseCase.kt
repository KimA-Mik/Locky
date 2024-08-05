package com.github.kima_mik.locky.domain.packages.useCase

import com.github.kima_mik.locky.domain.applicationData.useCase.GetAppDataUseCase
import com.github.kima_mik.locky.domain.packages.model.PackageEntry
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class SubscribeToPackageEntriesUseCase(
    private val getInstalledPackages: GetInstalledPackagesUseCase,
    private val getAppData: GetAppDataUseCase
) {
    operator fun invoke() = combine(
        getInstalledPackages(),
        getAppData()
            .map { it.lockedPackages }
            .distinctUntilChanged()
            .map { it.toSet() }
    ) { packagesState, lockedPackages ->
        when (packagesState) {
            is GetInstalledPackagesUseCase.Result.Loading -> Result.Loading(
                packagesState.progress
            )

            is GetInstalledPackagesUseCase.Result.Success -> {
                val entries = packagesState.data.map { pack ->
                    val locked = lockedPackages.contains(pack.packageName)
                    PackageEntry.fromPInfo(pInfo = pack, locked = locked)
                }
                Result.Success(entries)
            }
        }
    }

    sealed interface Result {
        data class Loading(val progress: Float) : Result
        data class Success(val data: List<PackageEntry>) : Result
    }
}