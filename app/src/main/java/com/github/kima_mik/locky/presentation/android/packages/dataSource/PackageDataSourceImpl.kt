package com.github.kima_mik.locky.presentation.android.packages.dataSource

import android.content.Context
import android.content.pm.PackageManager
import com.github.kima_mik.locky.domain.packages.dataSource.PackageDataSource
import com.github.kima_mik.locky.domain.packages.model.PInfo
import com.github.kima_mik.locky.presentation.android.packages.mappers.toPInfo
import com.github.kima_mik.locky.presentation.android.packages.util.isSystem


class PackageDataSourceImpl(context: Context) : PackageDataSource {
    private val packageManager: PackageManager = context.packageManager

    override fun getPackages(): List<PInfo> {
        val res = mutableListOf<PInfo>()

        val packs = packageManager.getInstalledPackages(0)
        for (pack in packs) {
            if (pack.isSystem()) {
                continue
            }

            res.add(pack.toPInfo(packageManager))
        }

        return res
    }
}