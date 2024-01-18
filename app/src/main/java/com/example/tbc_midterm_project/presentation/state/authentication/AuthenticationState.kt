package com.example.tbc_midterm_project.presentation.state.authentication

import com.example.tbc_midterm_project.presentation.model.UserPres

data class AuthenticationState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccessful: Boolean = false,
    val user: UserPres = UserPres("", "")
)