package com.example.tbc_midterm_project.domain.repository

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface IDataStoreRepository {
    suspend fun saveBoolean(key: Preferences.Key<Boolean>, value: Boolean)

    fun readBoolean(key: Preferences.Key<Boolean>): Flow<Boolean>

    suspend fun clear()
}