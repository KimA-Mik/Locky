package com.github.kima_mik.locky.data.applicationData

import androidx.datastore.core.DataStore
import com.github.kima_mik.locky.domain.applicationData.AppDataRepository
import kotlinx.coroutines.flow.map

class AppDataRepositoryImpl(private val store: DataStore<AppDataDto>) : AppDataRepository {
    override val data = store.data.map { it.toAppData() }

    override suspend fun updateLocked(locked: Boolean) {
        updateData {
            it.copy(
                locked = locked
            )
        }
    }

    override suspend fun updatePassword(password: List<Char>) {
        updateData {
            it.copy(
                password = password
            )
        }
    }

    override suspend fun updateLockedPackages(lockedPackages: List<String>) {
        updateData {
            it.copy(
                lockedPackages = lockedPackages
            )
        }
    }

    private suspend fun updateData(
        transform: suspend (AppDataDto) -> AppDataDto
    ): AppDataDto {
        return store.updateData(transform)
    }
}