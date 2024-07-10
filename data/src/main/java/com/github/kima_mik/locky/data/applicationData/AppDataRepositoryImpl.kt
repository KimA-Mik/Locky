package com.github.kima_mik.locky.data.applicationData

import com.github.kima_mik.locky.domain.applicationData.AppDataRepository
import kotlinx.coroutines.flow.map


typealias AppDataTransform = suspend (AppDataDto) -> AppDataDto

class AppDataRepositoryImpl(
    private val wrapper: AppDataWrapper,
) : AppDataRepository {
    override val data = wrapper.dtoData.map { it.toAppData() }
    private var _buffer: List<String>? = null

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

    override fun setTemporalBuffer(buffer: List<String>) {
        _buffer = buffer
    }

    override fun checkTemporalBuffer(buffer: List<String>): Boolean {
        return _buffer == buffer
    }

    private suspend fun update(transform: AppDataTransform) {
        wrapper.update(transform)
    }
}