package com.github.kima_mik.locky.domain.packages.useCase

import com.github.kima_mik.locky.domain.applicationData.useCase.GetAppDataUseCase
import com.github.kima_mik.locky.domain.packages.model.PackageEntry
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class SubscribeToPackageEntriesUseCase(
    private val getInstalledPackages: GetInstalledPackagesUseCase,
    private val getAppData: GetAppDataUseCase
) {
    operator fun invoke() = flow {
        val packages = getInstalledPackages()

        getAppData()
            .map { it.lockedPackages }
            .distinctUntilChanged()
            .map { it.toSet() }
            .collect { lockedPackages ->
                val updated = packages.map { pack ->
                    val locked = lockedPackages.contains(pack.packageName)
                    PackageEntry.fromPInfo(pInfo = pack, locked = locked)
                }
                emit(updated)
            }
    }
}