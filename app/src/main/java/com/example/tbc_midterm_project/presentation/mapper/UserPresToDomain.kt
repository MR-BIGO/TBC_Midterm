package com.example.tbc_midterm_project.presentation.mapper

import com.example.tbc_midterm_project.domain.model.User
import com.example.tbc_midterm_project.presentation.model.UserPres

fun UserPres.toDomain() : User {
    return User(
        email = email,
        password = password
    )
}