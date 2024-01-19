package com.example.tbc_midterm_project.presentation.screen.calculator

import androidx.lifecycle.ViewModel
import com.example.tbc_midterm_project.presentation.event.calculator.CalculatorEvents
import com.example.tbc_midterm_project.presentation.state.calculator.CalculatorState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CalculatorFragmentViewModel @Inject constructor() : ViewModel() {

    private val _calculatorState = MutableStateFlow(CalculatorState())
    val calculatorState: SharedFlow<CalculatorState> = _calculatorState.asStateFlow()

    fun onEvent(event: CalculatorEvents){
        when(event){
            is CalculatorEvents.CalculatePressed -> {}
        }
    }
}