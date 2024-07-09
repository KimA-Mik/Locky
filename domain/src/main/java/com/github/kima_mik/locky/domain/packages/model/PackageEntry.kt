package com.github.kima_mik.locky.domain.packages.model

import android.graphics.Bitmap

data class PackageEntry(
    val appName: String,
    val packageName: String,
    val icon: Bitmap?,
    val locked: Boolean
) {
    companion object {
        fun fromPInfo(pInfo: PInfo, locked: Boolean): PackageEntry {
            return PackageEntry(
                appName = pInfo.appName,
                packageName = pInfo.packageName,
                icon = pInfo.icon,
                locked = locked,
            )
        }
    }
}
