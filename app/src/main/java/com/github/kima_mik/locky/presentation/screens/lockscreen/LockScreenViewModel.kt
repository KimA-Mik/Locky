package com.github.kima_mik.locky.presentation.screens.lockscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kima_mik.locky.domain.applicationData.useCase.GetAppDataUseCase
import com.github.kima_mik.locky.domain.code.DEFAULT_CODE_LENGTH
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
    private val symbols = MutableStateFlow(List<String?>(DEFAULT_CODE_LENGTH) { null })
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

    private var cursor = 0

    fun onEvent(event: LockScreenUserEvent) {
        when (event) {
            is LockScreenUserEvent.KeyboardPress -> onKeyboardPress(event.event)
        }
    }

    private fun onKeyboardPress(event: KeyboardEvent) {
        when (event) {
            KeyboardEvent.Backspace -> deleteSymbol()
            KeyboardEvent.Enter -> {}
            is KeyboardEvent.Number -> enterSymbol(event.code.toString())
        }
    }

    private fun enterSymbol(symbol: String) {
        if (cursor == DEFAULT_CODE_LENGTH) {
            return
        }

        updateSymbols(cursor, symbol)
        cursor += 1
    }

    private fun deleteSymbol() {
        cursor = (cursor - 1).coerceAtLeast(0)
        if (symbols.value[cursor] == null) return

        updateSymbols(cursor, null)
    }

    private fun updateSymbols(index: Int, value: String?) {
        val list = symbols.value.toMutableList()
        list[index] = value
        symbols.value = list
    }
}