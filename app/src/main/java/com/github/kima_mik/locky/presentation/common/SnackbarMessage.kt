package com.github.kima_mik.locky.presentation.common

data class SnackbarMessage(
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)