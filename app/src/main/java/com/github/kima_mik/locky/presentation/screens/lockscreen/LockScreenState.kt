package com.github.kima_mik.locky.presentation.screens.lockscreen

import com.github.kima_mik.locky.domain.code.DEFAULT_CODE_LENGTH

data class LockScreenState(
    val hideInput: Boolean = false,
    val symbols: List<String?> = List(DEFAULT_CODE_LENGTH) { null },
    val flowState: LockScreenFlow = LockScreenFlow.LAUNCH
)

