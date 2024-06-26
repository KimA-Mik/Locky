package com.github.kima_mik.locky.data.applicationState

import kotlinx.serialization.Serializable

@Serializable
data class AppData(
    val locked: Boolean = false,
    val password: List<Char> = emptyList(),
    val lockedPackages: List<String> = emptyList()
)
