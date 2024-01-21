package com.example.tbc_midterm_project.presentation.event.offer

sealed class ExerciseEvents {
    data class AddToCollectionPressed(val id: Int): ExerciseEvents()
    data object BackPressed : ExerciseEvents()
    data object ResetError: ExerciseEvents()

    data class LoadExercises(val id: Int): ExerciseEvents()
}