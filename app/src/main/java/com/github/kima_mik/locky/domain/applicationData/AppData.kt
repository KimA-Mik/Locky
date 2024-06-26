package com.github.kima_mik.locky.domain.applicationData

data class AppData(
    val locked: Boolean,
    val password: List<Char>,
    val lockedPackages: List<String>
)
