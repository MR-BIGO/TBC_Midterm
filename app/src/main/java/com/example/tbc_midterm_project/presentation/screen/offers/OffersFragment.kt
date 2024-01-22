package com.example.tbc_midterm_project.presentation.screen.offers

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbc_midterm_project.databinding.FragmentOffersBinding
import com.example.tbc_midterm_project.presentation.event.home.HomeEvents
import com.example.tbc_midterm_project.presentation.event.offer.OfferEvents
import com.example.tbc_midterm_project.presentation.screen.base.BaseFragment
import com.example.tbc_midterm_project.presentation.screen.home.HomeFragmentArgs
import com.example.tbc_midterm_project.presentation.state.offers.OffersState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OffersFragment : BaseFragment<FragmentOffersBinding>(FragmentOffersBinding::inflate) {

    private val viewModel: OffersFragmentViewModel by viewModels()
    private lateinit var offersRecyclerAdapter: OfferItemRecyclerAdapter
    private val args: OffersFragmentArgs by navArgs()
    override fun setUp() {
        setUpRv()
        listeners()
        bindObservers()
    }

    private fun setUpRv() {
        offersRecyclerAdapter = OfferItemRecyclerAdapter()
        binding.rvPackages.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = offersRecyclerAdapter
        }
    }

    private fun listeners() {
        offersRecyclerAdapter.itemOnClick = { id ->
            viewModel.onEvent(OfferEvents.OfferPressed(id, args.auth))
        }
        binding.btnBack.setOnClickListener {
            viewModel.onEvent(OfferEvents.BackPressed)
        }
    }

    private fun handleState(state: OffersState) {
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        state.data?.let {
            offersRecyclerAdapter.setData(it)
            offersRecyclerAdapter.notifyItemRangeChanged(0, it.size)
        }

        state.error?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.onEvent(OfferEvents.ResetError)
        }
    }

    private fun handleEvent(event: OffersFragmentViewModel.NavigationEvents) {
        when(event){
            is OffersFragmentViewModel.NavigationEvents.NavigateBack -> navigateBack()
            is OffersFragmentViewModel.NavigationEvents.NavigateToExercises -> navigateToExercises(event.id)
            is OffersFragmentViewModel.NavigationEvents.ShowAlertForSignedOut -> showAlertDialog()
            is OffersFragmentViewModel.NavigationEvents.NavigateToLogIn -> navigateToLogIn()
        }
    }

    private fun navigateBack(){
        findNavController().navigateUp()
    }

    private fun navigateToExercises(id: Int){
        findNavController().navigate(OffersFragmentDirections.actionOffersFragmentToExercisesFragment(id))
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setTitle("You can't access this data without an account. Proceed to Log In Page?")
            setCancelable(true)
            setPositiveButton("Yes") { _, _ ->
                viewModel.onEvent(OfferEvents.LogInPressed)
            }
            setNeutralButton("cancel") { dialog, _ ->
                dialog.dismiss()
            }
        }
        builder.show()
    }

    private fun navigateToLogIn(){
        findNavController().navigate(OffersFragmentDirections.actionOffersFragmentToLoginFragment())
    }

    private fun bindObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.offersState.collect {
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