package com.github.kima_mik.locky

import android.app.Application
import com.github.kima_mik.locky.presentation.di.data
import com.github.kima_mik.locky.presentation.di.domain
import com.github.kima_mik.locky.presentation.di.presentation
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class LockyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@LockyApplication)
            modules(
                data(),
                domain(),
                presentation()
            )
        }
    }
}