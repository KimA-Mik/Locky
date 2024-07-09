package com.github.kima_mik.locky.presentation.android.dataStore.appData

import androidx.datastore.core.DataStore
import com.github.kima_mik.locky.data.applicationData.AppDataDto
import com.github.kima_mik.locky.data.applicationData.AppDataTransform
import com.github.kima_mik.locky.data.applicationData.AppDataWrapper
import kotlinx.coroutines.flow.Flow

class AppDataWrapperImpl(private val appdataStore: DataStore<AppDataDto>) : AppDataWrapper {
    override val dtoData: Flow<AppDataDto> = appdataStore.data

    override suspend fun update(transform: AppDataTransform): AppDataDto {
        return appdataStore.updateData {
            transform(it)
        }
    }
}