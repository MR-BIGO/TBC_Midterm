package com.example.tbc_midterm_project.di

import com.example.tbc_midterm_project.BuildConfig
import com.google.firebase.auth.FirebaseAuth


import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

//    @Provides
//    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
//        return HttpLoggingInterceptor().apply {
//            // Set the log level to NONE when it's not a debug build
//            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
//        }
//    }
//    @Provides
//    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
//        val builder = OkHttpClient.Builder()
//        // Add the logging interceptor in debug mode
//        if (BuildConfig.DEBUG) {
//            builder.addInterceptor(loggingInterceptor)
//        }
//        return builder.build()
//    }

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

//    @Provides
//    @Singleton
//    fun provideFirebaseDatabase(okHttpClient: OkHttpClient): DatabaseReference {
//        val firebaseDatabase = FirebaseDatabase.getInstance(BuildConfig.API_URL).apply {
//            setPersistenceEnabled(true)
//        }
//        val databaseReference = firebaseDatabase.reference
//        val httpClient = databaseReference.javaClass.getDeclaredField("client")
//        httpClient.isAccessible = true
//        httpClient.set(databaseReference, okHttpClient)
//
//        return databaseReference
//    }

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): DatabaseReference {
        return FirebaseDatabase.getInstance(BuildConfig.API_URL).reference
    }
}