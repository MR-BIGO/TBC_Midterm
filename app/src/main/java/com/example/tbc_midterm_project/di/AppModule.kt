package com.example.tbc_midterm_project.di

import com.example.tbc_midterm_project.data.common.HandleAuth
import com.example.tbc_midterm_project.data.repository.FirebaseAuthenticatorRepositoryImpl
import com.example.tbc_midterm_project.domain.repository.IFirebaseAuthenticatorRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseAuthenticatorRepository(
        handleAuth: HandleAuth,
        firebaseAuth: FirebaseAuth
    ): IFirebaseAuthenticatorRepository {
        return FirebaseAuthenticatorRepositoryImpl(handleAuth, firebaseAuth)
    }

    @Singleton
    @Provides
    fun provideHandleAuth(): HandleAuth = HandleAuth()
}