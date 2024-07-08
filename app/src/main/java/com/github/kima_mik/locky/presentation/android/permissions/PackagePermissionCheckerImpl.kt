package com.github.kima_mik.locky.presentation.android.permissions

import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.icu.util.Calendar
import com.github.kima_mik.locky.domain.permissions.PackagePermissionChecker


class PackagePermissionCheckerImpl(private val usageStatsManager: UsageStatsManager) : PackagePermissionChecker {
    override fun isPackageUsagePermissionGranted(): Boolean {
        val calendar: Calendar = Calendar.getInstance()
        val endTime: Long = calendar.getTimeInMillis()
        calendar.add(Calendar.DATE, -1)
        val startTime: Long = calendar.getTimeInMillis()

        val uEvents: UsageEvents = usageStatsManager.queryEvents(startTime, endTime)

        return uEvents.hasNextEvent()
    }
}


