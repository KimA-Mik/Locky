package com.github.kima_mik.locky.presentation.screens.lockscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kima_mik.locky.domain.applicationData.useCase.GetAppDataUseCase
import com.github.kima_mik.locky.presentation.elements.keyboard.KeyboardEvent
import com.github.kima_mik.locky.presentation.screens.lockscreen.event.LockScreenUserEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LockScreenViewModel(
    appData: GetAppDataUseCase
) : ViewModel() {
    private val hideInput = MutableStateFlow(false)
    private val symbols = MutableStateFlow(List<String?>(4) { null })
    private val flowState = MutableStateFlow(LockScreenFlow.LAUNCH)

    init {
        viewModelScope.launch(Dispatchers.Default) {
            val data = appData().first()
            if (data.password.isEmpty()) {
                flowState.value = LockScreenFlow.FIRST_LAUNCH
            }
        }
    }

    val state = combine(
        hideInput,
        symbols,
        flowState
    ) { hideInput, symbols, flowState ->
        LockScreenState(
            hideInput = hideInput,
            symbols = symbols,
            flowState = flowState
        )
    }

    private var cursor = -1

    fun onEvent(event: LockScreenUserEvent) {
        when (event) {
            is LockScreenUserEvent.KeyboardPress -> onKeyboardPress(event.event)
        }
    }

    private fun onKeyboardPress(event: KeyboardEvent) {
        when (event) {
            KeyboardEvent.Backspace -> {
                if (cursor < 0) {
                    return
                }
                updateSymbols(cursor, null)
                cursor = (cursor - 1)
            }

            KeyboardEvent.Enter -> {}
            is KeyboardEvent.Number -> {
                if (cursor == symbols.value.size - 1) {
                    return
                }
                cursor += 1
                updateSymbols(cursor, event.code.toString())
            }
        }
    }

    private fun updateSymbols(index: Int, value: String?) {
        val list = symbols.value.toMutableList()
        list[index] = value
        symbols.value = list
    }
}