package com.example.tbc_midterm_project.presentation.state.offers

import com.example.tbc_midterm_project.presentation.model.exercise_items.ExercisePres

data class ExercisesState(
    val isLoading: Boolean = false,
    val data: List<ExercisePres>? = null,
    val error: String? = null
)