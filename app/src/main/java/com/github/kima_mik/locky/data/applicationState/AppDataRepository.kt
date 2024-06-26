package com.github.kima_mik.locky.data.applicationState

import kotlinx.coroutines.flow.Flow

interface AppDataRepository {
    val data: Flow<AppData>

    suspend fun updateLocked(locked: Boolean)
    suspend fun updatePassword(password: List<Char>)
    suspend fun updateLockedPackages(lockedPackages: List<String>)
}