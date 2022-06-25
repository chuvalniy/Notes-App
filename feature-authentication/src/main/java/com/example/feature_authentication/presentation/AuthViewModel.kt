package com.example.feature_authentication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_authentication.domain.use_case.SignInUseCase
import com.example.feature_authentication.domain.use_case.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase
): ViewModel() {

    var email: String = ""
    var password: String = ""

    fun registerUser() {
        viewModelScope.launch {
            signInUseCase(email, password)
        }
    }

    fun loginUser() {
        viewModelScope.launch {
            signUpUseCase(email, password)
        }
    }
}