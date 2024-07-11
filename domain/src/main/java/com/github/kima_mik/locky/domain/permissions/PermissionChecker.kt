package com.github.kima_mik.locky.domain.permissions

interface PermissionChecker {
    fun isPackageUsagePermissionGranted(): Boolean
}