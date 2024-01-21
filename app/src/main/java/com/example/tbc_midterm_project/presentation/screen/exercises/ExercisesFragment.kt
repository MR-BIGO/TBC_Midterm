package com.example.tbc_midterm_project.presentation.screen.exercises

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbc_midterm_project.databinding.FragmentExercisesBinding
import com.example.tbc_midterm_project.presentation.event.offer.ExerciseEvents
import com.example.tbc_midterm_project.presentation.event.offer.OfferEvents
import com.example.tbc_midterm_project.presentation.screen.base.BaseFragment
import com.example.tbc_midterm_project.presentation.state.offers.ExercisesState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ExercisesFragment : BaseFragment<FragmentExercisesBinding>(FragmentExercisesBinding::inflate) {

    private val viewModel: ExercisesFragmentViewModel by viewModels()
    private lateinit var exerciseItemsAdapter: ExerciseItemsAdapter
    private val args: ExercisesFragmentArgs by navArgs()

    override fun setUp() {

        listeners()
        setUpRv()
        bindObservers()
        loadData()
    }

    private fun loadData(){
        viewModel.onEvent(ExerciseEvents.LoadExercises(args.id))
    }

    private fun listeners(){
        binding.btnBack.setOnClickListener {
            viewModel.onEvent(ExerciseEvents.BackPressed)
        }
    }

    private fun setUpRv(){
        exerciseItemsAdapter = ExerciseItemsAdapter()
        binding.rvExercises.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = exerciseItemsAdapter
        }
    }

    private fun handleState(state: ExercisesState){
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        state.data?.let {
            exerciseItemsAdapter.setData(it)
            exerciseItemsAdapter.notifyItemRangeChanged(0, it.size)
        }

        state.error?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.onEvent(ExerciseEvents.ResetError)
        }
    }

    private fun handleEvent(event: ExercisesFragmentViewModel.NavigationEvents){
        when(event){
            is ExercisesFragmentViewModel.NavigationEvents.NavigateToOffers -> navigateBack()
        }
    }

    private fun navigateBack(){
        findNavController().navigateUp()
    }

    private fun bindObservers(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.exercisesState.collect{
                        handleState(it)
                    }
                }
                launch {
                    viewModel.uiEvent.collect{
                        handleEvent(it)
                    }
                }
            }
        }
    }
}