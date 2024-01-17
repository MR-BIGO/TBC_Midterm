package com.example.tbc_midterm_project.data.repository

import com.example.tbc_midterm_project.data.common.HandleAuth
import com.example.tbc_midterm_project.data.common.Resource
import com.example.tbc_midterm_project.data.mapper.toDto
import com.example.tbc_midterm_project.domain.model.User
import com.example.tbc_midterm_project.domain.repository.IFirebaseAuthenticatorRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FirebaseAuthenticatorRepositoryImpl @Inject constructor(
    private val safeAuth: HandleAuth,
    private val firebase: FirebaseAuth
) : IFirebaseAuthenticatorRepository {
    override suspend fun loginUser(user: User): Flow<Resource<Boolean>> {
        return safeAuth.safeAuthentication(
            user.toDto(),
            firebase::signInWithEmailAndPassword,
            onSuccess = { true })
    }

    override suspend fun registerUser(user: User): Flow<Resource<Boolean>> {
        return safeAuth.safeAuthentication(
            user.toDto(),
            firebase::createUserWithEmailAndPassword,
            onSuccess = { true })
    }

    override fun logOutUser() {
        firebase.signOut()
    }
}