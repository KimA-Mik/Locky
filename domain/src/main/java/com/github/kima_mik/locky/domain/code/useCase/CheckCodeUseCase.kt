package com.github.kima_mik.locky.domain.code.useCase

import com.github.kima_mik.locky.domain.applicationData.AppDataRepository
import kotlinx.coroutines.flow.first

class CheckCodeUseCase(private val repository: AppDataRepository) {
    suspend operator fun invoke(code: List<String>): Result {
        val state = repository.data.first()
        return if (code == state.password) {
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