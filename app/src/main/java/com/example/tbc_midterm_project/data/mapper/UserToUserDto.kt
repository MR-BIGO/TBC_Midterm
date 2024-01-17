package com.example.tbc_midterm_project.data.mapper

import com.example.tbc_midterm_project.data.model.UserDto
import com.example.tbc_midterm_project.domain.model.User

fun User.toDto(): UserDto {
    return UserDto(email = email, password = password)
}