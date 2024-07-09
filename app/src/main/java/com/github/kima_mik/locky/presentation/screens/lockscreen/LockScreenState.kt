package com.github.kima_mik.locky.presentation.screens.lockscreen

data class LockScreenState(
    val hideInput: Boolean = false,
    val symbols: List<String?> = List<String?>(4) { null }
)
