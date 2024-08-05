package com.github.kima_mik.locky.presentation.android.service.lockService

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.github.kima_mik.locky.domain.packages.dataSource.PackageDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.ConcurrentHashMap

class LockService : Service(), KoinComponent {
    private val binder = LockBinder()

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val packagesDataSource: PackageDataSource by inject()

    private var locked = setOf<String>()
    private var unlocked = ConcurrentHashMap<String, Long>()

    inner class LockBinder : Binder()

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        coroutineScope.launch { run() }
        coroutineScope.launch { checkLockTimeout() }
        return START_STICKY
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        coroutineScope.cancel()
    }

    private suspend fun run() = coroutineScope {
        while (isActive) {
            delay(DELAY_TIME)

            val foreground = packagesDataSource.getForegroundPackage() ?: continue
        }
    }

    private suspend fun checkLockTimeout() = coroutineScope {
        while (isActive) {
            delay(LOCK_TIMEOUT_INTERVAL)

            val currentTime = System.currentTimeMillis()
            var packageToDelete: String? = null

            unlocked.forEach { (pack, timestamp) ->
                if (currentTime - timestamp > LOCK_TIMEOUT) {
                    packageToDelete = pack
                }
            }

            packageToDelete?.let {
                unlocked.remove(it)
            }
        }
    }

    companion object {
        private const val DELAY_TIME = 250L

        //TODO: expose to settings
        private const val LOCK_TIMEOUT = 5 * 1000L
        private const val LOCK_TIMEOUT_INTERVAL = 1000L
    }
}