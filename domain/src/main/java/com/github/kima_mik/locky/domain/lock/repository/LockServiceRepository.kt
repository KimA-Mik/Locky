package com.github.kima_mik.locky.domain.lock.repository

interface LockServiceRepository {
    fun isServiceRunning(): Boolean
    fun startService()
    fun stopService()
}