package com.example.feature_authentication.presentation.sign_in

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
import com.example.feature_authentication.R
import com.example.feature_authentication.databinding.FragmentSignInBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : BaseFragment<FragmentSignInBinding>() {

    private val viewModel by stateViewModel<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUiEvent()

        handleButtonClicks()

        binding.etEmail.addTextChangedListener {
            viewModel.onEvent(SignInEvent.EmailChanged(it.toString()))
        }

        binding.etPassword.addTextChangedListener {
            viewModel.onEvent(SignInEvent.PasswordChanged(it.toString()))
        }
    }

    private fun observeUiEvent() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.singInEvent.collect { event ->
                when (event) {
                    is SignInViewModel.UiSignInEvent.NavigateToNoteListScreen -> {
                        findNavController().navigate(
                            Uri.parse("noteApp://noteList")
                        )
                    }
                    is SignInViewModel.UiSignInEvent.NavigateToRegisterScreen -> {
                        findNavController().navigate(R.id.navigate_to_sign_up)
                    }
                    is SignInViewModel.UiSignInEvent.ShowSnackbar -> {
                        Snackbar.make(requireView(), event.message, Snackbar.LENGTH_LONG).show()
                    }
                    is SignInViewModel.UiSignInEvent.ShowProgressBar -> {
                        binding.progressBar.isVisible = event.isLoading
                    }
                }
            }
        }
    }


    private fun handleButtonClicks() {
        binding.btnNewAccount.setOnClickListener {
            viewModel.onEvent(SignInEvent.RegisterNewAccountButtonClicked)
        }

        binding.btnLogin.setOnClickListener {
            viewModel.onEvent(SignInEvent.LoginButtonClicked)
        }
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSignInBinding.inflate(inflater, container, false)
}
