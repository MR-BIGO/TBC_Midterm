package com.example.tbc_midterm_project.domain.use_case.validator

import javax.inject.Inject

class PasswordValidatorUseCase @Inject constructor(){

    operator fun invoke(password: String): Boolean = password.length > 6
}