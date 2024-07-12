package com.github.kima_mik.locky.presentation.android.permissions

import android.app.AppOpsManager
import android.os.Build
import android.os.Process
import com.github.kima_mik.locky.domain.permissions.PermissionChecker


class PermissionCheckerImpl(
    private val appOpsManager: AppOpsManager,
    private val packageName: String
) : PermissionChecker {
    override fun isPackageUsagePermissionGranted(): Boolean {
        val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            appOpsManager.unsafeCheckOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                Process.myUid(), packageName
            )
        } else {
            appOpsManager.checkOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                Process.myUid(), packageName
            )
        }

        return mode == AppOpsManager.MODE_ALLOWED
    }

    override fun isManageOverlayPermissionGranted(): Boolean {
        val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            appOpsManager.unsafeCheckOpNoThrow(
                AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW,
                Process.myUid(), packageName
            )
        } else {
            appOpsManager.checkOpNoThrow(
                AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW,
                Process.myUid(), packageName
            )
        }

        return mode == AppOpsManager.MODE_ALLOWED
    }
}


