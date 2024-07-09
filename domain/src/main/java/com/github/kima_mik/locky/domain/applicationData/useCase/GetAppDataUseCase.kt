package com.github.kima_mik.locky.domain.applicationData.useCase

import com.github.kima_mik.locky.domain.applicationData.AppData
import com.github.kima_mik.locky.domain.applicationData.AppDataRepository
import kotlinx.coroutines.flow.map

class GetAppDataUseCase(private val repository: AppDataRepository) {
    operator fun invoke() = repository.data.map {
        AppData(
            locked = it.locked,
            password = it.password,
            lockedPackages = it.lockedPackages
        )
    }
}