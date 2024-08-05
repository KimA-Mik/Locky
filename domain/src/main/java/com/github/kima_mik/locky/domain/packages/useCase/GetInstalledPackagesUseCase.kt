package com.github.kima_mik.locky.domain.packages.useCase

import com.github.kima_mik.locky.domain.packages.dataSource.PackageDataSource
import com.github.kima_mik.locky.domain.packages.model.PInfo
import kotlinx.coroutines.flow.map

class GetInstalledPackagesUseCase(private val dataSource: PackageDataSource) {
    operator fun invoke() = dataSource.getPackages().map {
        when (it) {
            is PackageDataSource.PackageLoadingState.Loading -> Result.Loading(it.progress)
            is PackageDataSource.PackageLoadingState.Success -> Result.Success(it.data)
        }
    }

    sealed interface Result {
        data class Loading(val progress: Float) : Result
        data class Success(val data: List<PInfo>) : Result
    }
}