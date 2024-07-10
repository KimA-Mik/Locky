package com.github.kima_mik.locky.domain.code.useCase

import com.github.kima_mik.locky.domain.applicationData.AppDataRepository

class ConfirmNewCodeUseCase(private val repository: AppDataRepository) {
    operator fun invoke(code: List<String>): Result {
        return if (repository.checkTemporalBuffer(code)) {
            Result.SUCCESS
        } else {
            Result.WRONG_CODE
        }
    }

    enum class Result {
        SUCCESS,
        WRONG_CODE
    }
}