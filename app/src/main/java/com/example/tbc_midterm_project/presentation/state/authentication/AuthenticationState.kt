package com.example.tbc_midterm_project.presentation.state.authentication

data class AuthenticationState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccessful: Boolean = false
)