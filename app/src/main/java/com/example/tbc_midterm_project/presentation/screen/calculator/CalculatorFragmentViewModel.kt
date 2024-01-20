package com.example.tbc_midterm_project.presentation.screen.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbc_midterm_project.R
import com.example.tbc_midterm_project.data.common.Resource
import com.example.tbc_midterm_project.domain.use_case.calculator.CalculatorUseCase
import com.example.tbc_midterm_project.presentation.event.calculator.CalculatorEvents
import com.example.tbc_midterm_project.presentation.model.calculator.AnswerItem
import com.example.tbc_midterm_project.presentation.state.calculator.CalculatorState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculatorFragmentViewModel @Inject constructor(private val calculateUseCase: CalculatorUseCase) :
    ViewModel() {

    private val _calculatorState = MutableStateFlow(CalculatorState())
    val calculatorState: SharedFlow<CalculatorState> = _calculatorState.asStateFlow()

//    private val _uiEvent = MutableSharedFlow<UiEvents>()
//    val uiEvents: SharedFlow<UiEvents> get() = _uiEvent

    init {
        var data: MutableList<AnswerItem>? = mutableListOf()
        data?.apply {
            add(AnswerItem("Body mass index", R.drawable.ic_scale))
            add(AnswerItem("Max heart rate", R.drawable.ic_scale))
            add(AnswerItem("Ideal weight (kg)", R.drawable.ic_scale))
            add(AnswerItem("Water intake (liters)", R.drawable.ic_scale))
        }
        _calculatorState.update { currentState -> currentState.copy(data = data) }
        data = null
    }

    fun onEvent(event: CalculatorEvents) {
        when (event) {
            is CalculatorEvents.CalculatePressed -> calculate(
                event.sex,
                event.age,
                event.weight,
                event.height
            )
        }
    }

    private fun calculate(sex: String, age: Int, weight: Double, height: Double) {
        viewModelScope.launch {
            calculateUseCase(_calculatorState.value.data!!, sex, age, weight, height).collect {
                when (it) {
                    is Resource.Loading -> _calculatorState.update { currentsState ->
                        currentsState.copy(
                            isLoading = it.loading
                        )
                    }

                    is Resource.Success -> _calculatorState.update { currentState ->
                        currentState.copy(
                            data = it.data
                        )
                    }

                    is Resource.Error -> {}
                }
            }
        }
    }

//    sealed class UiEvents {
//        data class RecyclerUpdate(val items: List<AnswerItem>) : UiEvents()
//    }
}