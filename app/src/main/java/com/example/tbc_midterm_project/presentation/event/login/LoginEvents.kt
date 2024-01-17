package com.example.tbc_midterm_project.presentation.event.login

sealed class LoginEvents {
    data class LoginPressed(val email: String, val password: String) : LoginEvents()
    data object NoAccountPressed : LoginEvents()
    data object ContinueOfflinePressed : LoginEvents()
    data object ResetError : LoginEvents()
}