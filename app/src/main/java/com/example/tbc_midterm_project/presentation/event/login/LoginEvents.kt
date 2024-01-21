package com.example.tbc_midterm_project.presentation.event.login

sealed class LoginEvents {
    data class LoginPressed(val email: String, val password: String, val remember: Boolean) : LoginEvents()
    data object NoAccountPressed : LoginEvents()
    data object ContinueOfflinePressed : LoginEvents()
    data object ResetError : LoginEvents()
}