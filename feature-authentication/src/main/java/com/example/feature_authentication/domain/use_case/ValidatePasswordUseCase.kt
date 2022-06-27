package com.example.feature_authentication.domain.use_case

class ValidatePasswordUseCase {

    operator fun invoke(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password can't be blank"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}