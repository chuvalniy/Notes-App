package com.example.feature_authentication.domain.repository

interface AuthRepository {

    suspend fun signUp(email: String, password: String)

    suspend fun signIn(email: String, password: String)
}