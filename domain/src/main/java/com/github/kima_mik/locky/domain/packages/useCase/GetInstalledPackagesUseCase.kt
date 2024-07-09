package com.github.kima_mik.locky.domain.packages.useCase

import com.github.kima_mik.locky.domain.packages.dataSource.PackageDataSource
import com.github.kima_mik.locky.domain.packages.model.PInfo

class GetInstalledPackagesUseCase(private val dataSource: PackageDataSource) {
    operator fun invoke(): List<PInfo> {
        return dataSource.getPackages()
    }
}