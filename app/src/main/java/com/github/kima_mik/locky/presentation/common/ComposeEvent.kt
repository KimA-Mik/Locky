package com.github.kima_mik.locky.presentation.common

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable

class ComposeEvent<T>(private val data: T?) {
    private var consumed: Boolean = false

    @SuppressLint("ComposableNaming")
    @Composable
    fun consume(consumer: @Composable (T) -> Unit) {
        if (!consumed) {
            consumed = true
            data?.let { consumer(it)}
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ComposeEvent<*>

        if (consumed != other.consumed) return false
        if (data != other.data) return false

        return data == other.data
    }

    override fun hashCode(): Int {
        var result = consumed.hashCode()
        result = 31 * result + data.hashCode()

        return result
    }
}