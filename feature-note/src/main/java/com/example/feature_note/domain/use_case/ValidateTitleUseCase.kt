package com.example.feature_note.domain.use_case

import com.example.common.ui.UiText
import com.example.common.utils.ValidationResult
import com.example.feature_note.R

class ValidateTitleUseCase {

    operator fun invoke(title: String): ValidationResult {
        if (title.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(
                    R.string.blank_title_error
                )
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}