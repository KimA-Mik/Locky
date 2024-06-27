package com.github.kima_mik.locky.presentation.android.dataStore.appData

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.github.kima_mik.locky.data.applicationData.AppDataDto

val Context.appDataStore: DataStore<AppDataDto> by dataStore(
    fileName = "AppData.pb",
    serializer = AppDataSerializer
)