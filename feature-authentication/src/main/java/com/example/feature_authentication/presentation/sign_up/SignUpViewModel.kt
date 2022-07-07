package com.example.feature_authentication.presentation.sign_up

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.settings.UserSessionStorage
import com.example.common.ui.UiText
import com.example.common.utils.Constants
import com.example.common.utils.Resource
import com.example.common.utils.StateStringPropertyDelegate
import com.example.feature_authentication.R
import com.example.feature_authentication.domain.use_case.SignUpUseCase
import com.example.feature_authentication.domain.use_case.ValidateEmailUseCase
import com.example.feature_authentication.domain.use_case.ValidatePasswordUseCase
import com.example.feature_authentication.domain.use_case.ValidateRepeatedPasswordUseCase
import com.example.feature_note.domain.use_case.SynchronizeNotesUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateRepeatedPasswordUseCase: ValidateRepeatedPasswordUseCase,
    private val synchronizeNotesUseCase: SynchronizeNotesUseCase,
    userSessionStorage: UserSessionStorage,
    state: SavedStateHandle
) : ViewModel() {

    private var email by StateStringPropertyDelegate(
        state = state,
        key = "email",
        initialValue = Constants.EMPTY_VALUE
    )
    private var password by StateStringPropertyDelegate(
        state = state,
        key = "password",
        initialValue = Constants.EMPTY_VALUE
    )
    private var repeatedPassword by StateStringPropertyDelegate(
        state = state,
        key = "repeatedPassword",
        initialValue = Constants.EMPTY_VALUE
    )

    private val _signUpChannel = Channel<UiSignUpEvent>()
    val signUpEvent = _signUpChannel.receiveAsFlow()

    init {
        if (userSessionStorage.getUserSessionId().isNotBlank()) {
            navigateToNoteListScreen()
        }
    }

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.BackToLoginButtonClicked -> navigateToLoginScreen()
            is SignUpEvent.RegisterUserButtonClicked -> signUpUser()
            is SignUpEvent.EmailChanged -> email = event.email
            is SignUpEvent.PasswordChanged -> password = event.password
            is SignUpEvent.RepeatPasswordChanged -> repeatedPassword = event.repeatPassword
        }
    }

    private fun signUpUser() {
        if (!isValidationSuccessful()) {
            return
        }
        viewModelScope.launch {
            signUpUseCase(email, password).onEach { event ->
                when (event) {
                    is Resource.Error -> {
                        event.error?.let { errorMessage ->
                            showSnackbar(UiText.DynamicString(errorMessage))
                        } ?: showSnackbar(UiText.StringResource(R.string.unexpected_error))
                        showProgressBar(false)
                    }
                    is Resource.Loading -> showProgressBar(true)
                    is Resource.Success -> {
                        synchronizeNotesUseCase()
                        showProgressBar(false)
                        navigateToNoteListScreen()
                    }
                }
            }.launchIn(this)
        }
    }

    private fun isValidationSuccessful(): Boolean {
        val emailResult = validateEmailUseCase(email)
        if (!emailResult.successful) {
            showSnackbar(emailResult.errorMessage!!)
            return false
        }

        val passwordResult = validatePasswordUseCase(password)
        if (!passwordResult.successful) {
            showSnackbar(passwordResult.errorMessage!!)
            return false
        }

        val repeatedPasswordResult = validateRepeatedPasswordUseCase(password, repeatedPassword)
        if (!repeatedPasswordResult.successful) {
            showSnackbar(repeatedPasswordResult.errorMessage!!) // TODO
            return false
        }

        return true
    }

    private fun navigateToNoteListScreen() = viewModelScope.launch {
        _signUpChannel.send(UiSignUpEvent.NavigateToNoteListScreen)
    }


    private fun navigateToLoginScreen() = viewModelScope.launch {
        _signUpChannel.send(UiSignUpEvent.NavigateToLoginScreen)
    }

    private fun showSnackbar(message: UiText) = viewModelScope.launch {
        _signUpChannel.send(
            UiSignUpEvent.ShowSnackbar(message = message)
        )
    }

    private fun showProgressBar(isLoading: Boolean) = viewModelScope.launch {
        _signUpChannel.send(UiSignUpEvent.ShowProgressBar(isLoading))
    }

    sealed class UiSignUpEvent {
        data class ShowSnackbar(val message: UiText) : UiSignUpEvent()
        data class ShowProgressBar(val isLoading: Boolean) : UiSignUpEvent()
        object NavigateToLoginScreen : UiSignUpEvent()
        object NavigateToNoteListScreen : UiSignUpEvent()
    }
}