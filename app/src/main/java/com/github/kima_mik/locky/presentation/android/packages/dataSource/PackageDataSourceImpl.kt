package com.github.kima_mik.locky.presentation.android.packages.dataSource

import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.PackageManager
import com.github.kima_mik.locky.domain.common.util.SECOND_MS
import com.github.kima_mik.locky.domain.packages.dataSource.PackageDataSource
import com.github.kima_mik.locky.domain.packages.model.PInfo
import com.github.kima_mik.locky.presentation.android.packages.mappers.toPInfo
import com.github.kima_mik.locky.presentation.android.packages.util.isSystem


class PackageDataSourceImpl(context: Context, private val usageStatsManager: UsageStatsManager) :
    PackageDataSource {
    private val packageManager: PackageManager = context.packageManager

    override fun getPackages(): List<PInfo> {
        val res = mutableListOf<PInfo>()

        val packs = packageManager.getInstalledPackages(0)
        for (pack in packs) {
            if (pack.isSystem()) {
                continue
            }

            res.add(pack.toPInfo(packageManager))
        }

        return res
    }

    override fun getForegroundPackage(): String? {
        val end = System.currentTimeMillis()
        val begin = end - QUERY_INTERVAL
        val usageEvents = usageStatsManager.queryEvents(begin, end) ?: return null
        val event = UsageEvents.Event()

        var packageName: String? = null
        while (usageEvents.getNextEvent(event)) {
            when (event.eventType) {
                UsageEvents.Event.ACTIVITY_RESUMED -> {
                    packageName = event.packageName
                }

                UsageEvents.Event.ACTIVITY_PAUSED -> {
                    if (event.packageName == packageName) {
                        packageName = null
                    }
                }
            }
        }

        return packageName
    }

    companion object {
        private const val QUERY_INTERVAL = SECOND_MS * 30
    }
}