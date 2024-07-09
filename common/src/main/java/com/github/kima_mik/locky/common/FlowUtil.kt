package com.github.kima_mik.locky.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T> Flow<T>.distinctLatest(): Flow<T> = flow {
    var past: T? = null
    collect {
        if (it == past) return@collect
        past = it
        emit(it)
    }
}