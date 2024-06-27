package com.github.kima_mik.locky.domain.packages.useCase

import com.github.kima_mik.locky.domain.applicationData.AppDataRepository
import com.github.kima_mik.locky.domain.applicationData.useCase.GetAppDataUseCase
import kotlinx.coroutines.flow.first

class LockPackageUseCase(
    private val getAppData: GetAppDataUseCase,
    private val repository: AppDataRepository
) {
    suspend operator fun invoke(packageName: String) {
        val lockedPackages = getAppData().first().lockedPackages

        if (lockedPackages.contains(packageName)) return

        val updated = lockedPackages.toMutableList()
        updated.add(packageName)
        repository.updateLockedPackages(updated)
    }
}