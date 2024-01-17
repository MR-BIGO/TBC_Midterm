package com.example.tbc_midterm_project.presentation.screen.login

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbc_midterm_project.databinding.FragmentLoginBinding
import com.example.tbc_midterm_project.presentation.screen.base.BaseFragment
import com.example.tbc_midterm_project.presentation.event.login.LoginEvents
import com.example.tbc_midterm_project.presentation.state.authentication.AuthenticationState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginFragmentViewModel by viewModels()
    override fun setUp() {
        listeners()
        bindObservers()
    }

    private fun listeners() {
        with(binding) {
            btnLogin.setOnClickListener {
                viewModel.onEvent(
                    LoginEvents.LoginPressed(
                        etEmail.text.toString(),
                        etPassword.text.toString(),
                      //  checkRemember.isChecked
                    )
                )
            }

            tvNoAccount.setOnClickListener {
                viewModel.onEvent(LoginEvents.NoAccountPressed)
            }

            tvContinueOffline.setOnClickListener {
                viewModel.onEvent(LoginEvents.ContinueOfflinePressed)
            }
        }
    }

    private fun navigateToRegister() {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
    }

    private fun navigateToOfflineHome() {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
    }

    private fun handleEvent(event: LoginFragmentViewModel.NavigationEvents) {
        when (event) {
            is LoginFragmentViewModel.NavigationEvents.NavigateToRegister -> navigateToRegister()
            is LoginFragmentViewModel.NavigationEvents.NavigateToOnlineHome -> {navigateToOfflineHome()}
            is LoginFragmentViewModel.NavigationEvents.NavigateToOfflineHome -> navigateToOfflineHome()
        }
    }

    private fun handleState(state: AuthenticationState) {
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        state.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.onEvent(LoginEvents.ResetError)
        }
    }

    private fun bindObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.loginState.collect {
                        handleState(it)
                    }
                }
                launch {
                    viewModel.uiEvent.collect {
                        handleEvent(it)
                    }
                }
            }
        }
    }
}