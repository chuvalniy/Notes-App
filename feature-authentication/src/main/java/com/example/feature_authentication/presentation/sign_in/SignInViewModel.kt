package com.example.feature_authentication.presentation.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_authentication.domain.use_case.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    var email: String = ""
    var password: String = ""

    private val _authChannel = Channel<UiAuthEvent>()
    val authEvent = _authChannel.receiveAsFlow()

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.EmailChanged -> {
                email = event.email
            }
            is SignInEvent.PasswordChanged -> {
                password = event.password
            }
            is SignInEvent.RegisterNewAccountButtonClicked -> {
                viewModelScope.launch { _authChannel.send(UiAuthEvent.NavigateToRegisterScreen) }
            }
            is SignInEvent.LoginButtonClicked -> {
                viewModelScope.launch {
                    signUpUseCase(email, password)
                    _authChannel.send(UiAuthEvent.NavigateToNoteListScreen)
                }
            }
        }
    }

    sealed class UiAuthEvent {
        data class ShowSnackbar(val message: String) : UiAuthEvent()
        object NavigateToRegisterScreen : UiAuthEvent()
        object NavigateToNoteListScreen : UiAuthEvent()
    }
}