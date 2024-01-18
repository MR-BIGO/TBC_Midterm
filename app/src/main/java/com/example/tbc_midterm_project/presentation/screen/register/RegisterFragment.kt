package com.example.tbc_midterm_project.presentation.screen.register

import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbc_midterm_project.databinding.FragmentRegisterBinding
import com.example.tbc_midterm_project.presentation.event.login.LoginEvents
import com.example.tbc_midterm_project.presentation.event.register.RegisterEvents
import com.example.tbc_midterm_project.presentation.screen.base.BaseFragment
import com.example.tbc_midterm_project.presentation.state.authentication.AuthenticationState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterFragmentViewModel by viewModels()
    override fun setUp() {
        listeners()
        bindObservers()
    }

    private fun listeners() {
        with(binding) {
            btnRegister.setOnClickListener {
                if (etEmail.text!!.isNotEmpty() && etPassword.text!!.isNotEmpty() && etRepeatPassword.text!!.isNotEmpty()){
                    viewModel.onEvent(
                        RegisterEvents.RegisterPressed(
                            etEmail.text.toString(),
                            etPassword.text.toString(),
                            etRepeatPassword.text.toString()
                        )
                    )
                }else{
                    setErrors(etEmail, etPassword, etRepeatPassword)
                }
            }

            tvAlreadyAccount.setOnClickListener {
                viewModel.onEvent(RegisterEvents.AlreadyAccountPressed)
            }
        }
    }

    private fun handleEvent(event: RegisterFragmentViewModel.NavigationEvents) {
        when(event){
            is RegisterFragmentViewModel.NavigationEvents.NavigateToLogin -> {
                navigateToLogin(event.email, event.password)
            }
        }
    }

    private fun handleState(state: AuthenticationState) {
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        state.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.onEvent(RegisterEvents.ResetError)
        }
    }

    private fun navigateToLogin(email: String?, password: String?){
        setFragmentResult(
            "RegisterResult",
            bundleOf(
                "email" to email,
                "password" to password
            )
        )
        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
    }

    private fun setErrors(emailEt: EditText, passwordEt: EditText, repeatPasswordEt: EditText){
        emailEt.error = "Please, Fill out all of the fields"
        passwordEt.error = "Please, Fill out all of the fields"
        repeatPasswordEt.error = "Please, Fill out all of the fields"
    }

    private fun bindObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.registerState.collect {
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