package com.example.feature_authentication.di

import com.example.feature_authentication.presentation.sign_in.SignInViewModel
import com.example.feature_authentication.presentation.sign_up.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authAppModule = module {

    viewModel {
        SignInViewModel(
            signInUseCase = get(),
            validateEmail = get(),
            validatePassword = get(),
            synchronizeNotesUseCase = get(),
            userSessionStorage = get()
        )
    }

    viewModel {
        SignUpViewModel(
            signUpUseCase = get(),
            validateEmailUseCase = get(),
            validatePasswordUseCase = get(),
            validateRepeatedPasswordUseCase = get(),
            synchronizeNotesUseCase = get(),
            userSessionStorage = get()
        )
    }
}