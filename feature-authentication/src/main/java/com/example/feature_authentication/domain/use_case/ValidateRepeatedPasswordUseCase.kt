package com.example.feature_authentication.domain.use_case

import com.example.common.ui.UiText
import com.example.common.utils.ValidationResult
import com.example.feature_authentication.R

class ValidateRepeatedPasswordUseCase {

    operator fun invoke(password: String, repeatedPassword: String): ValidationResult {
        if (repeatedPassword != password) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.repeated_password_error)
            )
        }

        return ValidationResult(successful = true)
    }
}