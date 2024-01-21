package com.example.tbc_midterm_project.domain.model

data class Offer (
    val exercises: List<Exercise>? = null,
    val id: Int? = 0,
    val image: String? = "",
    val isBookmarked: Boolean? = null,
    val nExercises: Int? = 0,
    val name: String? = ""
)