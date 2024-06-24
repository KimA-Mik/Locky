package com.github.kima_mik.locky.di

import com.github.kima_mik.locky.presentation.screens.applicationsList.ApplicationsListScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

fun presentation() = module {
    viewModelOf(::ApplicationsListScreenViewModel)
}