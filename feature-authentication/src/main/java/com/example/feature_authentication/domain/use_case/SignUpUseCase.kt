package com.example.feature_authentication.domain.use_case

import com.example.feature_authentication.domain.repository.AuthRepository

class SignUpUseCase(
    private val repository: AuthRepository
) {
    // TODO: add repeatedPassword parameter?
    operator fun invoke(
        email: String,
        password: String
    ) = repository.signUp(email, password)
}