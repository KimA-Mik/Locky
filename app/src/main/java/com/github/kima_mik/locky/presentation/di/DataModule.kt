package com.github.kima_mik.locky.presentation.di

import com.github.kima_mik.locky.data.applicationState.AppDataRepository
import com.github.kima_mik.locky.presentation.android.dataStore.appData.AppDataRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun data() = module {
    singleOf(::AppDataRepositoryImpl) bind AppDataRepository::class
}