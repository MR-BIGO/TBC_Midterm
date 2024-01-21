package com.example.tbc_midterm_project.presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbc_midterm_project.domain.use_case.datastore.GetStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashFragmentViewModel @Inject constructor(private val getStateUseCase: GetStateUseCase) :
    ViewModel() {

    private val _uiEvent = MutableSharedFlow<SplashUiEvent>()
    val uiEvent: SharedFlow<SplashUiEvent> get() = _uiEvent

    init {
        readSession()
    }

    private fun readSession() {
        viewModelScope.launch {
            getStateUseCase().collect {
                if (it)
                    _uiEvent.emit(SplashUiEvent.NavigateToHome)
                else
                    _uiEvent.emit(SplashUiEvent.NavigateToLogIn)
            }
        }
    }

    sealed interface SplashUiEvent {
        data object NavigateToLogIn : SplashUiEvent
        data object NavigateToHome : SplashUiEvent

    }
}