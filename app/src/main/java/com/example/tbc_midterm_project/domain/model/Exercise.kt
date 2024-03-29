package com.example.tbc_midterm_project.domain.model

data class Exercise(
    val desc: String? = "",
    val id: Int? = 0,
    val image: String? = "",
    val name: String? = "",
    val reps: Int? = 0,
    val rest: Int? = 0,
    val sets: Int? = 0
)