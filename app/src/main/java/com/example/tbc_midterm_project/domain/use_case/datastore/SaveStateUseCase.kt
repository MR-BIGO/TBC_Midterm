package com.example.tbc_midterm_project.domain.use_case.datastore

import com.example.tbc_midterm_project.domain.repository.IDataStoreRepository
import com.example.tbc_midterm_project.domain.user_auth_state.PreferenceKeys
import javax.inject.Inject

class SaveStateUseCase @Inject constructor(private val repository: IDataStoreRepository) {

    suspend operator fun invoke(state: Boolean) {
        repository.saveBoolean(key = PreferenceKeys.STATE, value = state)
    }
}