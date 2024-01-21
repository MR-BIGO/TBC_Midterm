package com.example.tbc_midterm_project.domain.model

data class Offer (
    val exercises: List<Exercise>,
    val id: Int,
    val image: String,
    val isBookmarked: Boolean,
    val nExercises: Int,
    val name: String
)