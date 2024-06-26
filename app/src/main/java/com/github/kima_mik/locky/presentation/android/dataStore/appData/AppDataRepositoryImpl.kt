package com.github.kima_mik.locky.presentation.android.dataStore.appData

import android.content.Context
import com.github.kima_mik.locky.data.applicationState.AppData
import com.github.kima_mik.locky.data.applicationState.AppDataRepository

class AppDataRepositoryImpl(context: Context) : AppDataRepository {
    private val store = context.appDataStore
    override val data = store.data

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
        transform: suspend (AppData) -> AppData
    ): AppData {
        return store.updateData(transform)
    }
}