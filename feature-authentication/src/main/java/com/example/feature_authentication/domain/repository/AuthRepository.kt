package com.example.feature_authentication.domain.repository

import com.example.feature_authentication.presentation.utils.AuthState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun signUp(email: String, password: String): Flow<AuthState>

    fun signIn(email: String, password: String): Flow<AuthState>
}