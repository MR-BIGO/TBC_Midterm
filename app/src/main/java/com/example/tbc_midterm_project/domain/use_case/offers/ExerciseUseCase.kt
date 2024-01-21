package com.example.tbc_midterm_project.domain.use_case.offers

import com.example.tbc_midterm_project.data.common.Resource
import com.example.tbc_midterm_project.domain.model.Exercise
import com.example.tbc_midterm_project.domain.repository.IFirebaseDatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExerciseUseCase @Inject constructor(private val repository: IFirebaseDatabaseRepository) {

    suspend operator fun invoke(id: Int): Flow<Resource<List<Exercise>>> {
        return flow{
            repository.getExercises(id).collect{
                when(it){
                    is Resource.Success -> emit(Resource.Success(data = it.data))
                    is Resource.Error -> emit(Resource.Error(it.error))
                    is Resource.Loading -> emit(Resource.Loading(it.loading))
                }
            }
        }
    }
}