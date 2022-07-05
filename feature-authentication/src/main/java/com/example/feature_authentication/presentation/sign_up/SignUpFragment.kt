package com.example.feature_authentication.presentation.sign_up

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.common.ui.BaseFragment
import com.example.feature_authentication.databinding.FragmentSignUpBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {

    private val viewModel by stateViewModel<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUiEvent()
        handleButtonClicks()

        binding.etEmail.addTextChangedListener {
            viewModel.onEvent(SignUpEvent.EmailChanged(it.toString()))
        }
        binding.etPassword.addTextChangedListener {
            viewModel.onEvent(SignUpEvent.PasswordChanged(it.toString()))
        }
        binding.etPasswordRepeat.addTextChangedListener {
            viewModel.onEvent(SignUpEvent.RepeatPasswordChanged(it.toString()))
        }
    }

    private fun handleButtonClicks() {
        binding.btnGoToLogin.setOnClickListener {
            viewModel.onEvent(SignUpEvent.BackToLoginButtonClicked)
        }

        binding.btnRegister.setOnClickListener {
            viewModel.onEvent(SignUpEvent.RegisterUserButtonClicked)
        }
    }

    private fun observeUiEvent() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.signUpEvent.collect { event ->
                when (event) {
                    is SignUpViewModel.UiSignUpEvent.NavigateToLoginScreen -> {
                        findNavController().popBackStack()
                    }
                    is SignUpViewModel.UiSignUpEvent.NavigateToNoteListScreen -> {

                        findNavController().navigate(
                            Uri.parse("noteApp://noteList")
                        )
                    }
                    is SignUpViewModel.UiSignUpEvent.ShowProgressBar -> {
                        binding.progressBar.isVisible = event.isLoading
                    }
                    is SignUpViewModel.UiSignUpEvent.ShowSnackbar -> {
                        Snackbar.make(requireView(), event.message, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSignUpBinding.inflate(inflater, container, false)
}