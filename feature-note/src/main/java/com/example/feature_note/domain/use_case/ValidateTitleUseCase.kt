package com.example.feature_note.domain.use_case

import com.example.common.utils.ValidationResult

class ValidateTitleUseCase {

    operator fun invoke(title: String): ValidationResult {
        if (title.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The title can't be blank"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}