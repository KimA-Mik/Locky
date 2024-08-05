package com.github.kima_mik.locky.domain.packages.dataSource

import com.github.kima_mik.locky.domain.packages.model.PInfo
import kotlinx.coroutines.flow.Flow

interface PackageDataSource {
    fun getPackages(): Flow<PackageLoadingState>
    fun getForegroundPackage(): String?

    sealed interface PackageLoadingState {
        data class Loading(val progress: Float) : PackageLoadingState
        data class Success(val data: List<PInfo>) : PackageLoadingState
    }
}