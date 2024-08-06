package com.github.kima_mik.locky.domain.lock

import com.github.kima_mik.locky.domain.applicationData.AppDataRepository

class LockApplicationsUseCase(
    private val appDataRepository: AppDataRepository
) {
    suspend operator fun invoke() {
        appDataRepository.updateLocked(true)
    }
}