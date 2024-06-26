package com.github.kima_mik.locky.domain.applicationData

import com.github.kima_mik.locky.data.applicationData.AppDataDto
import kotlinx.coroutines.flow.Flow

interface AppDataRepository {
    val data: Flow<AppDataDto>

    suspend fun updateLocked(locked: Boolean)
    suspend fun updatePassword(password: List<Char>)
    suspend fun updateLockedPackages(lockedPackages: List<String>)
}