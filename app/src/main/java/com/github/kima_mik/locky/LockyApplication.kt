package com.github.kima_mik.locky

import android.app.Application
import android.app.usage.UsageStatsManager
import android.content.Context
import com.github.kima_mik.locky.presentation.di.data
import com.github.kima_mik.locky.presentation.di.domain
import com.github.kima_mik.locky.presentation.di.presentation
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class LockyApplication : Application() {
    lateinit var usageStatsManager: UsageStatsManager
        private set

    override fun onCreate() {
        super.onCreate()
        usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

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