package com.example.feature_authentication.data.repository

import android.util.Log
import com.example.feature_authentication.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun signUp(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("AuthTest", "successfully created user")
                } else {
                    Log.d("AuthTest", "failed to create user ${task.exception}")
                }
            }
    }

    override suspend fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("AuthTest", "successfully logged in")
                } else {
                    Log.d("AuthTest", "failed to log in ${task.exception}")
                }
            }
    }

}