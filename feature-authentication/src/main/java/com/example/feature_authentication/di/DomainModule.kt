package com.example.feature_authentication.di

import com.example.feature_authentication.domain.use_case.*
import com.example.feature_note.domain.use_case.ValidateTitleUseCase
import org.koin.dsl.module

val authDomainModule = module {

    factory {
        SignInUseCase(repository = get())
    }

    factory {
        SignUpUseCase(repository = get())
    }

    factory {
        ValidateEmailUseCase()
    }

    factory {
        ValidatePasswordUseCase()
    }

    factory {
        ValidateRepeatedPasswordUseCase()
    }
}