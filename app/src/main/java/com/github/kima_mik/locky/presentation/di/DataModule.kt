package com.github.kima_mik.locky.presentation.di

import com.github.kima_mik.locky.data.applicationData.AppDataRepositoryImpl
import com.github.kima_mik.locky.data.applicationData.AppDataWrapper
import com.github.kima_mik.locky.domain.applicationData.AppDataRepository
import com.github.kima_mik.locky.presentation.android.dataStore.appData.AppDataWrapperImpl
import com.github.kima_mik.locky.presentation.android.dataStore.appData.appDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun data() = module {
    single { AppDataWrapperImpl(androidContext().appDataStore) } bind AppDataWrapper::class

    singleOf(::AppDataRepositoryImpl) bind AppDataRepository::class
}