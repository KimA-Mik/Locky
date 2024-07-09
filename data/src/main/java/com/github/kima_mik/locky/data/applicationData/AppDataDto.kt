package com.github.kima_mik.locky.data.applicationData

import com.github.kima_mik.locky.domain.applicationData.AppData
import kotlinx.serialization.Serializable

@Serializable
data class AppDataDto(
    val locked: Boolean = false,
    val password: List<Char> = emptyList(),
    val lockedPackages: List<String> = emptyList()
) {
    fun toAppData(): AppData {
        return AppData(
            locked = locked,
            password = password,
            lockedPackages = lockedPackages
        )
    }
}
