package com.example.tbc_midterm_project.presentation.model.exercise_items

import com.example.tbc_midterm_project.domain.model.Exercise

data class OfferPres(val exercises: List<Exercise>? = null,
                     val id: Int? = 0,
                     val image: String? = "",
                     val isBookmarked: Boolean? = null,
                     val nExercises: Int? = 0,
                     val name: String? = "")
