package com.example.feature_authentication.presentation.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_authentication.domain.use_case.SignInUseCase
import com.example.feature_authentication.presentation.utils.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private var email: String = ""
    private var password: String = ""

    private val _signInChannel = Channel<UiAuthEvent>()
    val singInEvent = _signInChannel.receiveAsFlow()

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.EmailChanged -> {
                email = event.email
            }
            is SignInEvent.PasswordChanged -> {
                password = event.password
            }
            is SignInEvent.RegisterNewAccountButtonClicked -> {
                viewModelScope.launch { _signInChannel.send(UiAuthEvent.NavigateToRegisterScreen) }
            }
            is SignInEvent.LoginButtonClicked -> {
                viewModelScope.launch {
                    signInUseCase(email, password).onEach { authState ->
                        when (authState) {
                            is AuthState.Loading -> {
                                _signInChannel.send(UiAuthEvent.ShowProgressBar(true))
                            }
                            is AuthState.Success -> {
                                _signInChannel.send(UiAuthEvent.ShowProgressBar(false))
                            }
                            is AuthState.Error -> {
                                _signInChannel.send(UiAuthEvent.ShowSnackbar(authState.error ?: "1")) // TODO
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    sealed class UiAuthEvent {
        data class ShowSnackbar(val message: String) : UiAuthEvent()
        data class ShowProgressBar(val isLoading: Boolean): UiAuthEvent()
        object NavigateToRegisterScreen : UiAuthEvent()
        object NavigateToNoteListScreen : UiAuthEvent()
    }
}