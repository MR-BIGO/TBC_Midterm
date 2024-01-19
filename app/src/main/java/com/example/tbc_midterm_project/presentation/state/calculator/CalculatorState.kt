package com.example.tbc_midterm_project.presentation.state.calculator

import com.example.tbc_midterm_project.presentation.model.calculator.AnswerItem

data class CalculatorState(
    val isLoading: Boolean = false,
    val data: List<AnswerItem>? = null,
    val hasButtonClicked: Boolean = false,
    val error: String? = null
)