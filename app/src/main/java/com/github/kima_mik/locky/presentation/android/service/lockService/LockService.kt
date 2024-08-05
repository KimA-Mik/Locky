package com.github.kima_mik.locky.presentation.android.service.lockService

import android.app.ForegroundServiceStartNotAllowedException
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import com.github.kima_mik.locky.R
import com.github.kima_mik.locky.domain.packages.dataSource.PackageDataSource
import com.github.kima_mik.locky.presentation.android.dataStore.appData.appDataStore
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

private const val TAG = "LockService"

class LockService : Service(), KoinComponent {
    private val binder = LockBinder()

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val packagesDataSource: PackageDataSource by inject()

    private var locked = setOf<String>()
    private var unlocked = ConcurrentHashMap<String, Long>()

    inner class LockBinder : Binder() {
        fun unlockPackage(packageName: String) {
            unlocked[packageName] = System.currentTimeMillis()
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!runForeground()) return START_STICKY
        coroutineScope.launch { run() }
        coroutineScope.launch { checkLockTimeout() }
        coroutineScope.launch { subscribeToSettings() }
        return START_STICKY
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        coroutineScope.cancel()
    }

    private fun runForeground(): Boolean {
        try {

            val notification = NotificationCompat.Builder(this, "CHANNEL_ID")
                //.setContentTitle()
                .setSmallIcon(R.drawable.lock_24)
                .build()

            ServiceCompat.startForeground(this, 100, notification, 0)
            return true
        } catch (e: Exception) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
                && e is ForegroundServiceStartNotAllowedException
            ) {
                Log.e(
                    TAG,
                    "App not in a valid state to start foreground service (e.g. started from bg)"
                )
            }
            return false
        }
    }


    private suspend fun run() = coroutineScope {
        while (isActive) {
            delay(DELAY_TIME)

            val foreground = packagesDataSource.getForegroundPackage() ?: continue

            if (unlocked.contains(foreground)) {
                unlocked[foreground] = System.currentTimeMillis()
                continue
            }

            if (locked.contains(foreground)) {
                //TODO: Launch lock activity
                Log.d(TAG, "Locking $foreground")
            }
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

    private suspend fun subscribeToSettings() {
        appDataStore.data.collect { data ->
            locked = data.lockedPackages.toSet()
        }
    }

    companion object {
        private const val DELAY_TIME = 250L

        //TODO: expose to settings
        private const val LOCK_TIMEOUT = 5 * 1000L
        private const val LOCK_TIMEOUT_INTERVAL = 1000L
    }
}