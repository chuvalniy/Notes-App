package com.example.feature_authentication.presentation.sign_up

sealed class SignUpEvent {
    object RegisterUserButtonClicked : SignUpEvent()
    object BackToLoginButtonClicked : SignUpEvent()
    data class EmailChanged(val email: String): SignUpEvent()
    data class PasswordChanged(val password: String): SignUpEvent()
    data class RepeatPasswordChanged(val repeatPassword: String): SignUpEvent()
}