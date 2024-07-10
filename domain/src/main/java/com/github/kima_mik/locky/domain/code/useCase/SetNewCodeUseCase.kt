package com.github.kima_mik.locky.domain.code.useCase

import com.github.kima_mik.locky.domain.applicationData.AppDataRepository
import com.github.kima_mik.locky.domain.code.DEFAULT_CODE_LENGTH

class SetNewCodeUseCase(private val repository: AppDataRepository) {
    operator fun invoke(code: List<String>): Result {
        if (code.size < DEFAULT_CODE_LENGTH) {
            return Result.TOO_SHORT
        }

        repository.setTemporalBuffer(code)
        return Result.SUCCESS
    }

    enum class Result {
        SUCCESS,
        TOO_SHORT,
    }
}