package com.github.kima_mik.locky.presentation.screens.applicationsList.mappers

import androidx.compose.ui.graphics.asImageBitmap
import com.github.kima_mik.locky.domain.packages.model.PInfo
import com.github.kima_mik.locky.presentation.common.ImmutableImageBitmap
import com.github.kima_mik.locky.presentation.screens.applicationsList.model.AppEntry

fun PInfo.toAppEntry(): AppEntry {
    val imageBitmap = icon?.asImageBitmap()
    imageBitmap?.prepareToDraw()
    return AppEntry(
        name = appName,
        packageName = packageName,
        icon = ImmutableImageBitmap(imageBitmap)
    )
}