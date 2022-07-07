package com.example.feature_authentication.di

import com.example.feature_authentication.data.repository.AuthRepositoryImpl
import com.example.feature_authentication.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import org.koin.dsl.module


val authDataModule = module {

    single<AuthRepository> {
        AuthRepositoryImpl(
            firebaseAuth = get(),
            userSessionStorage = get()
        )
    }

    single<FirebaseAuth> {
        FirebaseAuth.getInstance()
    }
}