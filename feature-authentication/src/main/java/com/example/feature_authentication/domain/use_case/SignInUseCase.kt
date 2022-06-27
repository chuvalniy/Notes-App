package com.example.feature_authentication.domain.use_case

import com.example.feature_authentication.domain.repository.AuthRepository

class SignInUseCase(
    private val repository: AuthRepository
) {

    operator fun invoke(email: String, password: String) = repository.signIn(email, password)
}