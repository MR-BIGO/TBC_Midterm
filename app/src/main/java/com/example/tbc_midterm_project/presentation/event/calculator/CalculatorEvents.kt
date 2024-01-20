package com.example.tbc_midterm_project.presentation.event.calculator

sealed class CalculatorEvents {
    data class CalculatePressed(val sex: String, val age: Int, val height: Double, val weight: Double) :
        CalculatorEvents()
}