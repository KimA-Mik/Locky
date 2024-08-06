package com.github.kima_mik.locky.domain.lock

import com.github.kima_mik.locky.domain.applicationData.AppDataRepository

class UnlockApplicationsUseCase(
    private val appDataRepository: AppDataRepository
) {
    suspend operator fun invoke() {
        appDataRepository.updateLocked(false)
    }
}