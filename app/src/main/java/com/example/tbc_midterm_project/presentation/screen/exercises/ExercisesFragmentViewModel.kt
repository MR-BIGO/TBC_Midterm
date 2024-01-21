package com.example.tbc_midterm_project.presentation.screen.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbc_midterm_project.data.common.Resource
import com.example.tbc_midterm_project.domain.use_case.offers.ExerciseUseCase
import com.example.tbc_midterm_project.presentation.event.offer.ExerciseEvents
import com.example.tbc_midterm_project.presentation.mapper.toPres
import com.example.tbc_midterm_project.presentation.state.offers.ExercisesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExercisesFragmentViewModel @Inject constructor(private val exercisesUseCase: ExerciseUseCase) :
    ViewModel() {
    private val _exercisesState = MutableStateFlow(ExercisesState())
    val exercisesState: SharedFlow<ExercisesState> = _exercisesState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<NavigationEvents>()
    val uiEvent: SharedFlow<NavigationEvents> get() = _uiEvent

    fun onEvent(event: ExerciseEvents) {
        when (event) {
            is ExerciseEvents.AddToCollectionPressed -> {}
            is ExerciseEvents.BackPressed -> navigateToOffers()
            is ExerciseEvents.ResetError -> updateError(null)
            is ExerciseEvents.LoadExercises -> getExercises(event.id)
        }
    }

    private fun getExercises(id: Int) {
        viewModelScope.launch {
            exercisesUseCase(id).collect {
                when (it) {
                    is Resource.Success -> _exercisesState.update { currentState ->
                        currentState.copy(
                            data = it.data.map { offer -> offer.toPres() })
                    }

                    is Resource.Error -> updateError(it.error)
                    is Resource.Loading -> _exercisesState.update { currentState ->
                        currentState.copy(
                            isLoading = it.loading
                        )
                    }
                }
            }
        }
    }

    private fun updateError(error: String?) {
        _exercisesState.update { currentState -> currentState.copy(error = error) }
    }

    private fun navigateToOffers() {
        viewModelScope.launch {
            _uiEvent.emit(NavigationEvents.NavigateToOffers)
        }
    }


    sealed class NavigationEvents {
        data object NavigateToOffers : NavigationEvents()
    }
}