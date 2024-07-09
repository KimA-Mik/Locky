package com.github.kima_mik.locky.data.applicationData

import kotlinx.coroutines.flow.Flow

interface AppDataWrapper {
    val dtoData: Flow<AppDataDto>
    suspend fun update(transform: AppDataTransform): AppDataDto
}