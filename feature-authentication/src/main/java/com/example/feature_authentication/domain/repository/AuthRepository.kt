package com.example.feature_authentication.domain.repository

import com.example.common.utils.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun signUp(email: String, password: String): Flow<Resource<AuthResult>>

    fun signIn(email: String, password: String): Flow<Resource<AuthResult>>

}