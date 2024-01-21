package com.example.tbc_midterm_project.data.model


data class OfferDto(

    var exercises: List<ExerciseDto>? = null,

    val id: Int? = 0,

    val image: String? = "",

    val isBookmarked: Boolean? = null,

    val nexercises: Int? = 0,

    val name: String? = ""
)