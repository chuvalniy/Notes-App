package com.example.feature_authentication.presentation.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.Resource
import com.example.feature_authentication.domain.use_case.SignInUseCase
import com.example.feature_authentication.domain.use_case.ValidateEmailUseCase
import com.example.feature_authentication.domain.use_case.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val validateEmail: ValidateEmailUseCase,
    private val validatePassword: ValidatePasswordUseCase
) : ViewModel() {

    private var email: String = ""
    private var password: String = ""

    private val _signInChannel = Channel<UiSignInEvent>()
    val singInEvent = _signInChannel.receiveAsFlow()

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.EmailChanged -> email = event.email
            is SignInEvent.PasswordChanged -> password = event.password
            is SignInEvent.RegisterNewAccountButtonClicked -> navigateToRegisterScreen()
            is SignInEvent.LoginButtonClicked -> signInUser()
        }
    }

    private fun signInUser() {
        if (!isValidationSucceeded()) {
            return
        }
        viewModelScope.launch {
            signInUseCase(email, password).onEach { event ->
                when (event) {
                    is Resource.Loading -> showProgressBar(true)
                    is Resource.Success -> {
                        showProgressBar(false)
                        navigateToNoteListScreen()
                    }
                    is Resource.Error -> {
                        showSnackbar(event.error ?: "")
                        showProgressBar(false)
                    }
                }
            }.launchIn(this)
        }
    }

    private fun isValidationSucceeded(): Boolean {
        val emailResult = validateEmail(email)
        if (!emailResult.successful) {
            showSnackbar(emailResult.errorMessage ?: "")
            return false
        }

        val passwordResult = validatePassword(password)
        if (!passwordResult.successful) {
            showSnackbar(passwordResult.errorMessage ?: "")
            return false
        }

        return true
    }

    private fun navigateToNoteListScreen() {
        viewModelScope.launch {
            _signInChannel.send(UiSignInEvent.NavigateToNoteListScreen)
        }
    }

    private fun navigateToRegisterScreen() {
        viewModelScope.launch {
            _signInChannel.send(UiSignInEvent.NavigateToRegisterScreen)
        }
    }

    private fun showSnackbar(message: String) {
        viewModelScope.launch {
            _signInChannel.send(UiSignInEvent.ShowSnackbar(message))
        }
    }

    private fun showProgressBar(isLoading: Boolean) {
        viewModelScope.launch {
            _signInChannel.send(UiSignInEvent.ShowProgressBar(isLoading))
        }
    }

    sealed class UiSignInEvent {
        data class ShowSnackbar(val message: String) : UiSignInEvent()
        data class ShowProgressBar(val isLoading: Boolean) : UiSignInEvent()
        object NavigateToRegisterScreen : UiSignInEvent()
        object NavigateToNoteListScreen : UiSignInEvent()
    }
}