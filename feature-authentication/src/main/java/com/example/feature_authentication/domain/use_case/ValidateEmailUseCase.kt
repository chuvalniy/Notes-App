package com.example.feature_authentication.domain.use_case

import com.example.common.ui.UiText
import com.example.common.utils.ValidationResult
import com.example.feature_authentication.R

class ValidateEmailUseCase {

    operator fun invoke(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.empty_email_field)
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}