package com.github.kima_mik.locky.presentation.di

import com.github.kima_mik.locky.domain.applicationData.useCase.GetAppDataUseCase
import com.github.kima_mik.locky.domain.packages.dataSource.PackageDataSource
import com.github.kima_mik.locky.domain.packages.useCase.GetInstalledPackagesUseCase
import com.github.kima_mik.locky.presentation.android.packages.dataSource.PackageDataSourceImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun domain() = module {
    singleOf(::PackageDataSourceImpl) bind PackageDataSource::class

    singleOf(::GetAppDataUseCase)

    singleOf(::GetInstalledPackagesUseCase)
}