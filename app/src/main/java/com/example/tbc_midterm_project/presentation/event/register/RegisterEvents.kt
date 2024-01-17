package com.example.tbc_midterm_project.presentation.event.register

sealed class RegisterEvents {
    data class RegisterPressed(val email: String, val password: String): RegisterEvents()
    data object AlreadyAccountPressed : RegisterEvents()
}