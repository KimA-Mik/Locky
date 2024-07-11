package com.github.kima_mik.locky.presentation.screens.lockscreen.event

sealed interface LockScreenUiEvent {
    data object ShortCode : LockScreenUiEvent
    data object CodesNotEqual : LockScreenUiEvent
    data object Unlock : LockScreenUiEvent
    data object EnterApp : LockScreenUiEvent
    data object WrongCode : LockScreenUiEvent
}