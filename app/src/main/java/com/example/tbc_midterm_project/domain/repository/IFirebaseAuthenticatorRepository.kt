package com.example.tbc_midterm_project.domain.repository

import com.example.tbc_midterm_project.data.common.Resource
import com.example.tbc_midterm_project.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IFirebaseAuthenticatorRepository {
    suspend fun loginUser(user: User): Flow<Resource<Boolean>>
    suspend fun registerUser(user: User): Flow<Resource<Boolean>>
    fun logOutUser()
}