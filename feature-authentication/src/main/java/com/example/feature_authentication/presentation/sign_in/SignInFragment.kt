package com.example.feature_authentication.presentation.sign_in

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.common.ui.BaseFragment
import com.example.feature_authentication.R
import com.example.feature_authentication.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>() {

    private val viewModel: SignInViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUiEvent()

        handleButtonClicks()
    }

    private fun observeUiEvent() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.authEvent.collect { event ->
                when(event) {
                    is SignInViewModel.UiAuthEvent.NavigateToNoteListScreen -> {

                    }
                    is SignInViewModel.UiAuthEvent.NavigateToRegisterScreen -> {
                        findNavController().navigate(R.id.navigate_to_sign_up)
                    }
                    is SignInViewModel.UiAuthEvent.ShowSnackbar -> {

                    }
                }
            }
        }
    }

    private fun handleButtonClicks() {
        binding.etEmail.addTextChangedListener {
            viewModel.onEvent(SignInEvent.EmailChanged(it.toString()))
        }

        binding.etPassword.addTextChangedListener {
            viewModel.onEvent(SignInEvent.PasswordChanged(it.toString()))
        }

        binding.btnNewAccount.setOnClickListener {
            viewModel.onEvent(SignInEvent.RegisterNewAccountButtonClicked)
        }
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSignInBinding.inflate(inflater, container, false)
}
