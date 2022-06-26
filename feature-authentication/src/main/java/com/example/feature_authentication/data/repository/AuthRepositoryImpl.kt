package com.example.feature_authentication.data.repository

import android.util.Log
import com.example.feature_authentication.domain.repository.AuthRepository
import com.example.feature_authentication.presentation.utils.AuthState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override fun signUp(email: String, password: String) = flow {
        emit(AuthState.Loading)

        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("AuthTest", "successfully created user")
                    } else {
                        Log.d("AuthTest", "failed to create user ${task.exception}")
                    }
                }
        } catch (e: Exception) {
            emit(AuthState.Error(e.message))
        }
    }

    override fun signIn(email: String, password: String) = flow {
        emit(AuthState.Loading)

        try {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("AuthTest", "successfully logged in")
                    } else {
                        Log.d("AuthTest", "failed to log in ${task.exception}")
                    }
                }
        } catch (e: Exception) {
            emit(AuthState.Error("Excpetion: ${e.message}"))
        }
    }

}