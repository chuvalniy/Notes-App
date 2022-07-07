package com.example.feature_authentication.domain.use_case

import com.example.common.ui.UiText
import com.example.common.utils.ValidationResult
import com.example.feature_authentication.R

class ValidatePasswordUseCase {

    operator fun invoke(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.empty_password_field)
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}