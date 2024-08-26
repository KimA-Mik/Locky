package com.github.kima_mik.locky.presentation.android.service.lockService

import android.content.Context
import android.content.Intent
import android.os.Build
import com.github.kima_mik.locky.domain.lock.repository.LockServiceRepository
import com.github.kima_mik.locky.presentation.android.service.isServiceRunning

class LockServiceRepositoryImpl(private val context: Context) : LockServiceRepository {
    override fun isServiceRunning(): Boolean {
        return context.isServiceRunning<LockService>()
    }

    override fun startService() {
        val intent = Intent(context, LockService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }

    override fun stopService() {
        val intent = Intent(context, LockService::class.java)
        context.stopService(intent)
    }
}