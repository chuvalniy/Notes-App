package com.example.feature_authentication.domain.use_case

import com.example.common.utils.ValidationResult

class ValidateRepeatedPasswordUseCase {

    operator fun invoke(password: String, repeatedPassword: String): ValidationResult {
        if (repeatedPassword != password) {
            return ValidationResult(
                successful = false,
                errorMessage = "The passwords doesn't match"
            )
        }

        return ValidationResult(successful = true)
    }
}