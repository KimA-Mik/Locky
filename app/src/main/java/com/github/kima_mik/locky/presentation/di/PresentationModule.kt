package com.github.kima_mik.locky.presentation.di

import com.github.kima_mik.locky.presentation.screens.applicationsList.ApplicationsListScreenViewModel
import com.github.kima_mik.locky.presentation.screens.lockscreen.LockScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

fun presentation() = module {
    viewModelOf(::ApplicationsListScreenViewModel)
    viewModelOf(::LockScreenViewModel)
}