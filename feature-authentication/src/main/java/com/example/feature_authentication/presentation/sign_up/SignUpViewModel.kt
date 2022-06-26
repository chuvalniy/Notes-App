package com.example.feature_authentication.presentation.sign_up

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_authentication.domain.use_case.SignUpUseCase
import com.example.feature_authentication.presentation.utils.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
): ViewModel() {

    private var email: String = ""
    private var password: String = ""
    private var repeatPassword: String = ""

    private val _signUpChannel = Channel<UiSignUpEvent>()
    val signUpEvent = _signUpChannel.receiveAsFlow()

    fun onEvent(event: SignUpEvent) {
        when (event) {
            SignUpEvent.BackToLoginButtonClicked -> {
                viewModelScope.launch {
                    _signUpChannel.send(UiSignUpEvent.NavigateToLoginScreen)
                }
            }
            SignUpEvent.RegisterUserButtonClicked -> {
                viewModelScope.launch {
                    signUpUseCase(email, password).onEach { authState ->
                        when (authState) {
                            is AuthState.Error -> {
                                _signUpChannel.send(UiSignUpEvent.ShowSnackbar(authState.error ?: ""))
                            }
                            is AuthState.Loading -> {
                                _signUpChannel.send(UiSignUpEvent.ShowProgressBar(true))
                            }
                            is AuthState.Success -> {
                                _signUpChannel.send(UiSignUpEvent.ShowProgressBar(true))
                            }
                        }
                    }.launchIn(this)
                }
            }
            is SignUpEvent.EmailChanged -> {
                email = event.email
            }
            is SignUpEvent.PasswordChanged -> {
                password = event.password
            }
            is SignUpEvent.RepeatPasswordChanged -> {
                repeatPassword = event.repeatPassword
            }
        }
    }

    sealed class UiSignUpEvent {
        data class ShowSnackbar(val message: String): UiSignUpEvent()
        data class ShowProgressBar(val isLoading: Boolean): UiSignUpEvent()
        object NavigateToLoginScreen : UiSignUpEvent()
        object NavigateToNoteListScreen : UiSignUpEvent()
    }
}