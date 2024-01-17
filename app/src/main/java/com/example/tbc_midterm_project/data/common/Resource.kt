package com.example.tbc_midterm_project.data.common

sealed class Resource<T> {
    data class Success<T>(val success: Boolean) : Resource<T>()
    data class Error<T>(val error: String) : Resource<T>()
    data class Loading<T>(val loading: Boolean) : Resource<T>()
}