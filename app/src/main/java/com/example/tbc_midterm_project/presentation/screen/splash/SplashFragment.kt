package com.example.tbc_midterm_project.presentation.screen.splash

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbc_midterm_project.databinding.FragmentSplashBinding
import com.example.tbc_midterm_project.presentation.screen.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val viewModel: SplashFragmentViewModel by viewModels()
    override fun setUp() {
        bindObserves()
    }

    private fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect {
                    handleNavigationEvents(event = it)
                }
            }
        }
    }

    private fun handleNavigationEvents(event: SplashFragmentViewModel.SplashUiEvent) {
        when (event) {
            is SplashFragmentViewModel.SplashUiEvent.NavigateToLogIn -> navigateToLogin()
            is SplashFragmentViewModel.SplashUiEvent.NavigateToHome -> navigateToHome()
        }
    }

    private fun navigateToLogin() {
        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
    }

    private fun navigateToHome() {
        findNavController().navigate(
            SplashFragmentDirections.actionSplashFragmentToHomeFragment(
                true
            )
        )
    }

}