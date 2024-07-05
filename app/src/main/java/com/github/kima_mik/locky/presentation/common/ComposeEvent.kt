package com.github.kima_mik.locky.presentation.common

import androidx.compose.runtime.Composable

class ComposeEvent<T>(private val data: T?) {
    private var consumed: Boolean = false

    @Composable
    fun Consume(consumer: @Composable (T?) -> Unit) {
        if (!consumed) {
            consumed = true
            data?.let { consumer(it)}
        }
    }
}