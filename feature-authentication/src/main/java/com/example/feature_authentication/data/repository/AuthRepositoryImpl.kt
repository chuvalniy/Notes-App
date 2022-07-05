package com.example.feature_authentication.data.repository

import android.util.Log
import com.example.common.settings.UserSessionStorage
import com.example.common.utils.Resource
import com.example.feature_authentication.domain.repository.AuthRepository
import com.google.firebase.auth.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val userSessionStorage: UserSessionStorage
) : AuthRepository {

    override fun signUp(email: String, password: String): Flow<Resource<AuthResult>> = flow {
        emit(Resource.Loading())

        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            userSessionStorage.saveUserSessionId(result.user?.uid ?: "")
            emit(Resource.Success(result))
        } catch (e: FirebaseAuthUserCollisionException) {
            emit(Resource.Error(error = e.message))
        } catch (e: FirebaseAuthWeakPasswordException) {
            emit(Resource.Error(error = e.message))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            emit(Resource.Error(error = e.message))
        } catch (e: Exception) {
            emit(Resource.Error(error = e.message))
        }
    }

    override fun signIn(email: String, password: String): Flow<Resource<AuthResult>> = flow {
        emit(Resource.Loading())

        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            userSessionStorage.saveUserSessionId(result.user?.uid ?: "")
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(error = e.message))
        }
    }
}