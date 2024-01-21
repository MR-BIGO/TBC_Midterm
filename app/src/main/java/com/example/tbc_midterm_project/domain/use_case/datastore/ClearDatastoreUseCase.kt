package com.example.tbc_midterm_project.domain.use_case.datastore

import com.example.tbc_midterm_project.domain.repository.IDataStoreRepository
import javax.inject.Inject

class ClearDatastoreUseCase @Inject constructor(private val repository: IDataStoreRepository) {
    suspend operator fun invoke() {
        repository.clear()
    }
}