package com.example.feature_authentication.domain.use_case

class ValidateEmailUseCase {

    operator fun invoke(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The email can't be blank"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}