package com.github.kima_mik.locky.domain.packages.useCase

import com.github.kima_mik.locky.domain.applicationData.AppDataRepository
import com.github.kima_mik.locky.domain.applicationData.useCase.GetAppDataUseCase
import com.github.kima_mik.locky.domain.permissions.PermissionChecker
import kotlinx.coroutines.flow.first

class LockPackageUseCase(
    private val getAppData: GetAppDataUseCase,
    private val repository: AppDataRepository,
    private val checker: PermissionChecker,
) {
    private var dataUsageStats = false
    private var manageOverlay = false

    suspend operator fun invoke(packageName: String): Result {
        if (!dataUsageStats) {
            dataUsageStats = checker.isPackageUsagePermissionGranted()
            if (!dataUsageStats) {
                return Result.NoDataUsageStatsPermission
            }
        }

        if (!manageOverlay) {
            manageOverlay = checker.isManageOverlayPermissionGranted()
            if (!manageOverlay) {
                return Result.NoManageOverlayPermission
            }
        }

        val lockedPackages = getAppData().first().lockedPackages

        if (lockedPackages.contains(packageName)) return Result.Success

        val updated = lockedPackages.toMutableList()
        updated.add(packageName)
        repository.updateLockedPackages(updated)
        return Result.Success
    }

    sealed interface Result {
        data object Success : Result
        data object NoDataUsageStatsPermission : Result
        data object NoManageOverlayPermission : Result
    }
}