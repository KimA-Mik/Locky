package com.github.kima_mik.locky.presentation.android.packages.mappers

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.graphics.drawable.toBitmapOrNull
import com.github.kima_mik.locky.domain.packages.model.PInfo

fun PackageInfo.toPInfo(pm: PackageManager): PInfo {
    val versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        longVersionCode
    } else {
        versionCode.toLong()
    }

    return PInfo(
        appName = applicationInfo.loadLabel(pm).toString(),
        packageName = packageName,
        versionName = versionName,
        versionCode = versionCode,
        icon = applicationInfo.loadIcon(pm).toBitmapOrNull()
    )
}