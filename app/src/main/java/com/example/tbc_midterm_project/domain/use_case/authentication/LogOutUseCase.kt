package com.example.tbc_midterm_project.domain.use_case.authentication

import com.example.tbc_midterm_project.domain.repository.IFirebaseAuthenticatorRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(private val repository: IFirebaseAuthenticatorRepository) {
    operator fun invoke(){
        repository.logOutUser()
    }
}