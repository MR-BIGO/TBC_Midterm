package com.example.tbc_midterm_project.presentation.screen.splash

import androidx.navigation.fragment.findNavController
import com.example.tbc_midterm_project.databinding.FragmentSplashBinding
import com.example.tbc_midterm_project.presentation.screen.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    override fun setUp() {
        navigateToLogin()
    }

    private fun navigateToLogin(){
        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
    }

}