package com.github.kima_mik.locky.domain.packages.dataSource

import com.github.kima_mik.locky.domain.packages.model.PInfo

interface PackageDataSource {
    fun getPackages(): List<PInfo>
}