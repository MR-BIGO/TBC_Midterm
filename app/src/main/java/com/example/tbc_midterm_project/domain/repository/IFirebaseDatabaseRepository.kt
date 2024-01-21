package com.example.tbc_midterm_project.domain.repository

import com.example.tbc_midterm_project.data.common.Resource
import com.example.tbc_midterm_project.domain.model.Exercise
import com.example.tbc_midterm_project.domain.model.Offer
import kotlinx.coroutines.flow.Flow

interface IFirebaseDatabaseRepository {

    suspend fun getOffers(): Flow<Resource<List<Offer>>>
    suspend fun getExercises(id: Int): Flow<Resource<List<Exercise>>>
}