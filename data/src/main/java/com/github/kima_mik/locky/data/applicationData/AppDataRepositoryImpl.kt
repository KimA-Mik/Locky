package com.github.kima_mik.locky.data.applicationData

import com.github.kima_mik.locky.domain.applicationData.AppDataRepository
import kotlinx.coroutines.flow.map


typealias AppDataTransform = suspend (AppDataDto) -> AppDataDto

class AppDataRepositoryImpl(
    private val wrapper: AppDataWrapper,
) : AppDataRepository {
    override val data = wrapper.dtoData.map { it.toAppData() }

    override suspend fun updateLocked(locked: Boolean) {
        update {
            it.copy(
                locked = locked
            )
        }
    }

    override suspend fun updatePassword(password: List<Char>) {
        update {
            it.copy(
                password = password
            )
        }
    }

    override suspend fun updateLockedPackages(lockedPackages: List<String>) {
        update {
            it.copy(
                lockedPackages = lockedPackages
            )
        }
    }

    private suspend fun update(transform: AppDataTransform) {
        wrapper.update(transform)
    }
}