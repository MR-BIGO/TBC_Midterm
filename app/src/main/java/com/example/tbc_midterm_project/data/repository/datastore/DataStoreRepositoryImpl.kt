package com.example.tbc_midterm_project.data.repository.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.tbc_midterm_project.domain.repository.IDataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : IDataStoreRepository {
    override suspend fun saveBoolean(key: Preferences.Key<Boolean>, value: Boolean) {
        dataStore.edit { settings ->
            settings[key] = value
        }
    }

    override fun readBoolean(key: Preferences.Key<Boolean>) = dataStore.data
        .map { preferences ->
            preferences[key] ?: false
        }


    override suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }

}