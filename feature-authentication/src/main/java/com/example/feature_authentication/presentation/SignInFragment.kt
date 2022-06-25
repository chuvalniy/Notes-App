package com.example.feature_authentication.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.example.common.ui.BaseFragment
import com.example.feature_authentication.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>() {

    private val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etEmail.addTextChangedListener {
            viewModel.email = it.toString()
        }

        binding.etPassword.addTextChangedListener {
            viewModel.password = it.toString()
        }

        binding.btnLogin.setOnClickListener {
            viewModel.loginUser()
        }

        binding.btnRegister.setOnClickListener {
            viewModel.registerUser()
        }
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSignInBinding.inflate(inflater, container, false)
}
