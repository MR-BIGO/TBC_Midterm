package com.example.tbc_midterm_project.presentation.screen.offers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbc_midterm_project.data.common.Resource
import com.example.tbc_midterm_project.domain.use_case.datastore.GetStateUseCase
import com.example.tbc_midterm_project.domain.use_case.offers.OffersUseCase
import com.example.tbc_midterm_project.presentation.event.offer.OfferEvents
import com.example.tbc_midterm_project.presentation.mapper.toPres
import com.example.tbc_midterm_project.presentation.state.offers.OffersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OffersFragmentViewModel @Inject constructor(
    private val offersUseCase: OffersUseCase,
    private val getStateUseCase: GetStateUseCase
) : ViewModel() {

    private val _offersState = MutableStateFlow(OffersState())
    val offersState: SharedFlow<OffersState> = _offersState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<NavigationEvents>()
    val uiEvent: SharedFlow<NavigationEvents> get() = _uiEvent

    init {
        getOffers()
    }

    fun onEvent(event: OfferEvents) {
        when (event) {
            is OfferEvents.OfferPressed -> handleOfferedPressed(event.id, event.auth)
            is OfferEvents.ResetError -> updateError(null)
            is OfferEvents.BackPressed -> navigateBack()
            is OfferEvents.LogInPressed -> navigateToLogIn()
        }
    }

    private fun getOffers() {
        viewModelScope.launch {
            offersUseCase().collect {
                when (it) {
                    is Resource.Success -> _offersState.update { currentState ->
                        currentState.copy(
                            data = it.data.map { offer -> offer.toPres() })
                    }

                    is Resource.Error -> updateError(it.error)
                    is Resource.Loading -> _offersState.update { currentState ->
                        currentState.copy(
                            isLoading = it.loading
                        )
                    }
                }
            }
        }
    }

    private fun handleOfferedPressed(id: Int, auth: Boolean) {
        viewModelScope.launch {
            if (auth) {
                navigateToExercises(id)
            } else {
                _uiEvent.emit(NavigationEvents.ShowAlertForSignedOut)
            }
        }
    }

    private fun navigateToLogIn() {
        viewModelScope.launch {
            _uiEvent.emit(NavigationEvents.NavigateToLogIn)
        }
    }

    private fun updateError(error: String?) {
        _offersState.update { currentState -> currentState.copy(error = error) }
    }
//

    private fun navigateToExercises(id: Int) {
        viewModelScope.launch {
            _uiEvent.emit(NavigationEvents.NavigateToExercises(id))
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _uiEvent.emit(NavigationEvents.NavigateBack)
        }
    }

    sealed class NavigationEvents {
        data class NavigateToExercises(val id: Int) : NavigationEvents()
        data object NavigateBack : NavigationEvents()
        data object ShowAlertForSignedOut : NavigationEvents()
        data object NavigateToLogIn : NavigationEvents()
    }
}