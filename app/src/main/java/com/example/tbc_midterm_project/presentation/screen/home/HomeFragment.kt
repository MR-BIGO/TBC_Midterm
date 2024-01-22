package com.example.tbc_midterm_project.presentation.screen.home

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tbc_midterm_project.R
import com.example.tbc_midterm_project.databinding.FragmentHomeBinding
import com.example.tbc_midterm_project.presentation.event.home.HomeEvents
import com.example.tbc_midterm_project.presentation.screen.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val args: HomeFragmentArgs by navArgs()
    private val viewModel: HomeFragmentViewModel by viewModels()

    override fun setUp() {
        listeners()
        setUpSignInLogOutBtn()
        bindObservers()
    }

    private fun listeners() {
        with(binding) {
            if (args.isAuthed) {
                btnLogOutSignIn.setOnClickListener {
                    showAlertDialog("Are you sure you want to Log out?", HomeEvents.LogOutPressed)
                }
            } else {
                btnLogOutSignIn.setOnClickListener {
                    showAlertDialog("Are you sure you want to Sign in?", HomeEvents.SignInPressed)
                }
            }
            btnOffers.setOnClickListener {
                viewModel.onEvent(HomeEvents.OffersPressed)
            }

            btnCalculator.setOnClickListener {
                viewModel.onEvent(HomeEvents.CalculatorPressed)
            }
        }
    }

    private fun setUpSignInLogOutBtn() {
        with(binding) {
            if (args.isAuthed) btnLogOutSignIn.setText(R.string.log_out) else btnLogOutSignIn.setText(
                R.string.sign_in
            )
        }
    }

    private fun navigateToOffers() {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToOffersFragment(args.isAuthed))
    }

    private fun navigateToCalculator() {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCalculatorFragment())
    }

    private fun navigateToLogin() {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
    }

    private fun handleEvent(event: HomeFragmentViewModel.HomeNavigationEvents) {
        when (event) {
            is HomeFragmentViewModel.HomeNavigationEvents.NavigateToOffers -> navigateToOffers()
            is HomeFragmentViewModel.HomeNavigationEvents.NavigateToCalculator -> navigateToCalculator()
            is HomeFragmentViewModel.HomeNavigationEvents.NavigateToLogin -> navigateToLogin()
        }
    }

    private fun bindObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect {
                    handleEvent(it)
                }
            }
        }
    }

    private fun showAlertDialog(title: String, event: HomeEvents) {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setTitle(title)
            setCancelable(true)
            setPositiveButton("Yes") { _, _ ->
                viewModel.onEvent(event)
            }
            setNeutralButton("cancel") { dialog, _ ->
                dialog.dismiss()
            }
        }
        builder.show()
    }
}