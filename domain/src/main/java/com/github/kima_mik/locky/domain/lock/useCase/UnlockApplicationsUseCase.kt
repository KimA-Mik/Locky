package com.github.kima_mik.locky.domain.lock.useCase

import com.github.kima_mik.locky.domain.applicationData.AppDataRepository
import com.github.kima_mik.locky.domain.lock.repository.LockServiceRepository

class UnlockApplicationsUseCase(
    private val appDataRepository: AppDataRepository,
    private val lockServiceRepository: LockServiceRepository
) {
    suspend operator fun invoke() {
        lockServiceRepository.stopService()
        appDataRepository.updateLocked(false)
    }
}