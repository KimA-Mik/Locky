package com.github.kima_mik.locky.domain.code.useCase

import com.github.kima_mik.locky.domain.applicationData.AppDataRepository

class ConfirmNewCodeUseCase(private val repository: AppDataRepository) {
    suspend operator fun invoke(code: List<String>): Result {
        if (repository.checkTemporalBuffer(code)) {
            return Result.CODES_NOT_EQUAL
        }

        repository.updatePassword(code)
        return Result.SUCCESS
    }

    enum class Result {
        SUCCESS,
        CODES_NOT_EQUAL
    }
}