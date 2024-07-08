package com.github.kima_mik.locky.presentation.screens.lockscreen.event

import com.github.kima_mik.locky.presentation.elements.keyboard.KeyboardEvent

typealias OnLockScreenEvent = (LockScreenUserEvent) -> Unit

sealed interface LockScreenUserEvent {
    data class KeyboardPress(val event: KeyboardEvent) : LockScreenUserEvent
}