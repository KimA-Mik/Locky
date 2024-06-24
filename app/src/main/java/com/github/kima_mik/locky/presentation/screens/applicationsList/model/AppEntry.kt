package com.github.kima_mik.locky.presentation.screens.applicationsList.model

import androidx.compose.runtime.Immutable
import com.github.kima_mik.locky.presentation.common.ImmutableImageBitmap

@Immutable
data class AppEntry(val name: String, val icon: ImmutableImageBitmap)