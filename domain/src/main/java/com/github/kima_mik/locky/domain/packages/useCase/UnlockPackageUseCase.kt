package com.github.kima_mik.locky.domain.packages.useCase

import com.github.kima_mik.locky.domain.applicationData.AppDataRepository
import com.github.kima_mik.locky.domain.applicationData.useCase.GetAppDataUseCase
import kotlinx.coroutines.flow.first

class UnlockPackageUseCase(
    private val getAppData: GetAppDataUseCase,
    private val repository: AppDataRepository
) {
    suspend operator fun invoke(packageName: String) {
        val lockedPackages = getAppData().first().lockedPackages

        for (i in lockedPackages.indices) {
            if (lockedPackages[i] == packageName) {
                val updated = lockedPackages.toMutableList()
                updated.removeAt(i)
                repository.updateLockedPackages(updated)
                return
            }
        }
    }
}