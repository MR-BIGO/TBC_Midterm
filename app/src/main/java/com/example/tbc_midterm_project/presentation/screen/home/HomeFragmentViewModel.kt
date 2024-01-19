package com.example.tbc_midterm_project.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbc_midterm_project.domain.use_case.authentication.LogOutUseCase
import com.example.tbc_midterm_project.presentation.event.home.HomeEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(private val logOutUseCase: LogOutUseCase) :
    ViewModel() {

    private val _uiEvent = MutableSharedFlow<HomeNavigationEvents>()
    val uiEvent: SharedFlow<HomeNavigationEvents> get() = _uiEvent

    fun onEvent(event: HomeEvents) {
        viewModelScope.launch {
            when (event) {
                is HomeEvents.OffersPressed -> _uiEvent.emit(HomeNavigationEvents.NavigateToOffers)
                is HomeEvents.CollectionPressed -> _uiEvent.emit(HomeNavigationEvents.NavigateToCollection)
                is HomeEvents.CalculatorPressed -> _uiEvent.emit(HomeNavigationEvents.NavigateToCalculator)
                is HomeEvents.LogOutPressed -> handleLogOut()
                is HomeEvents.SignInPressed -> _uiEvent.emit(HomeNavigationEvents.NavigateToLogin)
            }
        }
    }

    private fun handleLogOut() {
        viewModelScope.launch {
            logOutUseCase()
            _uiEvent.emit(HomeNavigationEvents.NavigateToLogin)
        }
    }

    sealed class HomeNavigationEvents {
        data object NavigateToOffers : HomeNavigationEvents()
        data object NavigateToCollection : HomeNavigationEvents()
        data object NavigateToCalculator : HomeNavigationEvents()
        data object NavigateToLogin : HomeNavigationEvents()
    }
}