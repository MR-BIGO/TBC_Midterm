package com.example.tbc_midterm_project.presentation.model.calculator

data class AnswerItem(
    val section: String,
    val icon: Int,
    val result: Double = 0.0,
    val bmiResult: String = ""
)
