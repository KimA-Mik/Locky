package com.github.kima_mik.locky.domain.applicationData.useCase

import com.github.kima_mik.locky.domain.applicationData.AppDataRepository

class GetAppDataUseCase(private val repository: AppDataRepository) {
    operator fun invoke() = repository.data
}