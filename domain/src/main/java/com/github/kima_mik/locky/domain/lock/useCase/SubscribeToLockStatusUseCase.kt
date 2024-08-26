package com.github.kima_mik.locky.domain.lock.useCase

import com.github.kima_mik.locky.domain.applicationData.AppDataRepository
import com.github.kima_mik.locky.domain.lock.repository.LockServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class SubscribeToLockStatusUseCase(
    private val appData: AppDataRepository,
    private val lockServiceRepository: LockServiceRepository
) {
    operator fun invoke() =
        appData
            .data
            .flowOn(Dispatchers.IO)
            .map { it.locked }
            .distinctUntilChanged()
            .map { savedState ->
                val serviceState = lockServiceRepository.isServiceRunning()
                when {
                    savedState && !serviceState -> Result.ShouldRun
                    !savedState && serviceState -> Result.ShouldStop
                    savedState && serviceState -> Result.Running
                    else -> Result.Stopped
                }
            }

    sealed interface Result {
        data object Running : Result
        data object Stopped : Result
        data object ShouldRun : Result
        data object ShouldStop : Result
    }
}