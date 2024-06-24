package com.github.kima_mik.locky.presentation.android.packages.util

import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo

fun PackageInfo.isSystem(): Boolean {
    return applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
}