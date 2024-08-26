package com.github.kima_mik.locky.domain.permissions

class CheckPermissionUseCase(private val permissionChecker: PermissionChecker) {
    operator fun invoke(permission: String) = permissionChecker.checkDefaultPermission(permission)
}