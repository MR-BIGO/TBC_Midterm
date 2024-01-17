package com.example.tbc_midterm_project.domain.use_case.authentication

import com.example.tbc_midterm_project.data.common.Resource
import com.example.tbc_midterm_project.domain.model.User
import com.example.tbc_midterm_project.domain.repository.IFirebaseAuthenticatorRepository
import com.example.tbc_midterm_project.domain.use_case.validator.EmailValidatorUseCase
import com.example.tbc_midterm_project.domain.use_case.validator.PasswordValidatorUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: IFirebaseAuthenticatorRepository,
    private val validateEmail: EmailValidatorUseCase,
    private val validatePassword: PasswordValidatorUseCase
) {
    suspend operator fun invoke(user: User): Flow<Resource<Boolean>> {
        if (!validateEmail(user.email)) return flowOf(Resource.Error("Please, Enter a valid Email address"))

        else if (!validatePassword(user.password)) return flowOf(Resource.Error("Password should contain more than 6 letters!"))

        return repository.registerUser(user)
    }
}