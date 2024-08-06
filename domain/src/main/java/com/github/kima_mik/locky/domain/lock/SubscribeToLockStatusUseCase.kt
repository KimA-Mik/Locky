package com.github.kima_mik.locky.domain.lock

import com.github.kima_mik.locky.domain.applicationData.AppDataRepository
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class SubscribeToLockStatusUseCase(
    private val appData: AppDataRepository
) {
    operator fun invoke() =
        appData
            .data
            .map { it.locked }
            .distinctUntilChanged()
}