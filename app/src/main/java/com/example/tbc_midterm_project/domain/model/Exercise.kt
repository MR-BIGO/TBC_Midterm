package com.example.tbc_midterm_project.domain.model

data class Exercise(
    val desc: String,
    val id: Int,
    val image: String,
    val reps: Int,
    val rest: Int,
    val sets: Int
)