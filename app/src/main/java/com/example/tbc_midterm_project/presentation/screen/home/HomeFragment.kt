package com.example.tbc_midterm_project.presentation.screen.home

import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tbc_midterm_project.R
import com.example.tbc_midterm_project.databinding.FragmentHomeBinding
import com.example.tbc_midterm_project.presentation.screen.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val args: HomeFragmentArgs by navArgs()
    override fun setUp() {
        listeners()
        setUpSignInLogOutBtn()
    }

    private fun listeners() {
        with(binding) {
            if (args.isAuthed) {
                btnOffers.setOnClickListener {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToOffersFragment())
                }
                btnLibrary.setOnClickListener {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCollectionFragment())
                }
                btnLogOutSignIn.setOnClickListener {
                    showAlertDialog("Are you sure you want to Log out?")
                }
            } else {
                btnOffers.setOnClickListener {
                    showAlertDialog("You are currently Logged out. Do you wish to sign in?")
                }
                btnLibrary.setOnClickListener {
                    showAlertDialog("You are currently Logged out. Do you wish to sign in?")
                }
                btnLogOutSignIn.setOnClickListener {
                    showAlertDialog("Are you sure you want to Sign in?")
                }
            }
            btnCalculator.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCalculatorFragment())
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

    private fun showAlertDialog(title: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setTitle(title)
            setCancelable(true)
            setPositiveButton("Yes") { _, _ ->
                if (args.isAuthed) {

                } else {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
                }
            }
            setNeutralButton("cancel") { dialog, _ ->
                dialog.dismiss()
            }
        }
        builder.show()
    }
}