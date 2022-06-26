package com.example.feature_authentication.presentation.utils

sealed class AuthState {
    object Success : AuthState()
    class Error(val error: String?) : AuthState()
    object Loading : AuthState()
}