package com.github.kima_mik.locky

import android.app.AppOpsManager
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.usage.UsageStatsManager
import android.content.Context
import android.os.Build
import com.github.kima_mik.locky.presentation.di.data
import com.github.kima_mik.locky.presentation.di.domain
import com.github.kima_mik.locky.presentation.di.presentation
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class LockyApplication : Application() {
    lateinit var usageStatsManager: UsageStatsManager
        private set

    lateinit var appOpsManager: AppOpsManager
        private set

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
        usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        appOpsManager = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager

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

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.lock_status_notification_chanel_title)
            val descriptionText = getString(R.string.lock_status_notification_chanel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel =
                NotificationChannel(LOCK_STATUS_NOTIFICATION_CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    companion object {
        const val LOCK_STATUS_NOTIFICATION_CHANNEL_ID = "lock_status"
    }
}