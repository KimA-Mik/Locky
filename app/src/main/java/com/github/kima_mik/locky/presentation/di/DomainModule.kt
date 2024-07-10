package com.github.kima_mik.locky.presentation.di

import com.github.kima_mik.locky.LockyApplication
import com.github.kima_mik.locky.domain.applicationData.useCase.GetAppDataUseCase
import com.github.kima_mik.locky.domain.code.useCase.ConfirmNewCodeUseCase
import com.github.kima_mik.locky.domain.code.useCase.SetNewCodeUseCase
import com.github.kima_mik.locky.domain.packages.dataSource.PackageDataSource
import com.github.kima_mik.locky.domain.packages.useCase.GetInstalledPackagesUseCase
import com.github.kima_mik.locky.domain.packages.useCase.LockPackageUseCase
import com.github.kima_mik.locky.domain.packages.useCase.SubscribeToPackageEntriesUseCase
import com.github.kima_mik.locky.domain.packages.useCase.UnlockPackageUseCase
import com.github.kima_mik.locky.domain.permissions.PackagePermissionChecker
import com.github.kima_mik.locky.presentation.android.packages.dataSource.PackageDataSourceImpl
import com.github.kima_mik.locky.presentation.android.permissions.PackagePermissionCheckerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun domain() = module {
    single { (androidContext() as LockyApplication).usageStatsManager }

    singleOf(::PackageDataSourceImpl) bind PackageDataSource::class
    singleOf(::PackagePermissionCheckerImpl) bind PackagePermissionChecker::class

    singleOf(::GetAppDataUseCase)

    singleOf(::GetInstalledPackagesUseCase)
    singleOf(::LockPackageUseCase)
    singleOf(::SubscribeToPackageEntriesUseCase)
    singleOf(::UnlockPackageUseCase)

    singleOf(::ConfirmNewCodeUseCase)
    singleOf(::SetNewCodeUseCase)
}