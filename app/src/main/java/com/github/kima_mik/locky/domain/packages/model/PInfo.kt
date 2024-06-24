package com.github.kima_mik.locky.domain.packages.model

import android.graphics.Bitmap

data class PInfo(
    val appName: String,
    val packageName: String,
    val versionName: String,
    val versionCode: Long,
    //leave for now
    val icon: Bitmap?
)
