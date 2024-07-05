package com.github.kima_mik.locky.domain.permissions

interface PackagePermissionChecker {
    fun isPackageUsagePermissionGranted(): Boolean
}