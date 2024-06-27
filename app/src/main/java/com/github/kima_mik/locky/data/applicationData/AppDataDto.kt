package com.github.kima_mik.locky.data.applicationData

import kotlinx.serialization.Serializable

@Serializable
data class AppDataDto(
    val locked: Boolean = false,
    val password: List<Char> = emptyList(),
    val lockedPackages: List<String> = emptyList()
)
