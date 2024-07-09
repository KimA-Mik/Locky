package com.github.kima_mik.locky.domain.packages.useCase

import com.github.kima_mik.locky.domain.applicationData.AppDataRepository
import com.github.kima_mik.locky.domain.applicationData.useCase.GetAppDataUseCase
import com.github.kima_mik.locky.domain.permissions.PackagePermissionChecker
import kotlinx.coroutines.flow.first

class LockPackageUseCase(
    private val getAppData: GetAppDataUseCase,
    private val repository: AppDataRepository,
    private val checker: PackagePermissionChecker,
) {
    private var permissionGranted = false

    suspend operator fun invoke(packageName: String): Result {
        if (!permissionGranted) {
            permissionGranted = checker.isPackageUsagePermissionGranted()
            if (!permissionGranted) {
                return Result.NoPermission
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
        data object NoPermission : Result
    }
}