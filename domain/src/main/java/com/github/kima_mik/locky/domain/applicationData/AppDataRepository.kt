package com.github.kima_mik.locky.domain.applicationData

import kotlinx.coroutines.flow.Flow

interface AppDataRepository {
    val data: Flow<AppData>

    suspend fun updateLocked(locked: Boolean)
    suspend fun updatePassword(password: List<String>)
    suspend fun updateLockedPackages(lockedPackages: List<String>)
    fun setTemporalBuffer(buffer: List<String>)
    fun checkTemporalBuffer(buffer: List<String>): Boolean
}