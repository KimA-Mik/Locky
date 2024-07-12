package com.github.kima_mik.locky.presentation.di

import com.github.kima_mik.locky.LockyApplication
import com.github.kima_mik.locky.domain.applicationData.useCase.GetAppDataUseCase
import com.github.kima_mik.locky.domain.code.useCase.CheckCodeUseCase
import com.github.kima_mik.locky.domain.code.useCase.ConfirmNewCodeUseCase
import com.github.kima_mik.locky.domain.code.useCase.SetNewCodeUseCase
import com.github.kima_mik.locky.domain.packages.dataSource.PackageDataSource
import com.github.kima_mik.locky.domain.packages.useCase.GetInstalledPackagesUseCase
import com.github.kima_mik.locky.domain.packages.useCase.LockPackageUseCase
import com.github.kima_mik.locky.domain.packages.useCase.SubscribeToPackageEntriesUseCase
import com.github.kima_mik.locky.domain.packages.useCase.UnlockPackageUseCase
import com.github.kima_mik.locky.domain.permissions.PermissionChecker
import com.github.kima_mik.locky.presentation.android.packages.dataSource.PackageDataSourceImpl
import com.github.kima_mik.locky.presentation.android.permissions.PermissionCheckerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun domain() = module {
    single { (androidContext() as LockyApplication).usageStatsManager }

    singleOf(::PackageDataSourceImpl) bind PackageDataSource::class
    single {
        val context = androidContext() as LockyApplication
        PermissionCheckerImpl(
            appOpsManager = context.appOpsManager,
            packageName = context.packageName
        )
    } bind PermissionChecker::class

    singleOf(::GetAppDataUseCase)

    singleOf(::GetInstalledPackagesUseCase)
    singleOf(::LockPackageUseCase)
    singleOf(::SubscribeToPackageEntriesUseCase)
    singleOf(::UnlockPackageUseCase)

    singleOf(::CheckCodeUseCase)
    singleOf(::ConfirmNewCodeUseCase)
    singleOf(::SetNewCodeUseCase)
}