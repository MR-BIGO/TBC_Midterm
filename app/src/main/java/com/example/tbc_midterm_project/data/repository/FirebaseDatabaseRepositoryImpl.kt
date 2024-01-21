package com.example.tbc_midterm_project.data.repository

import android.util.Log.d
import com.example.tbc_midterm_project.data.common.Resource
import com.example.tbc_midterm_project.data.mapper.toDomain
import com.example.tbc_midterm_project.data.model.ExerciseDto
import com.example.tbc_midterm_project.data.model.OfferDto
import com.example.tbc_midterm_project.domain.model.Exercise
import com.example.tbc_midterm_project.domain.model.Offer
import com.example.tbc_midterm_project.domain.repository.IFirebaseDatabaseRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseDatabaseRepositoryImpl @Inject constructor(private val firebaseDb: DatabaseReference) :
    IFirebaseDatabaseRepository {
    override suspend fun getOffers(): Flow<Resource<List<Offer>>> {

        return flow {
            emit(Resource.Loading(true))
            try {
                val dataList: MutableList<Offer> = mutableListOf()
                val dataSnapshot: DataSnapshot = withContext(Dispatchers.IO) {
                    firebaseDb.child("offers").get().await()
                }
                for (i in dataSnapshot.children) {
                    val offerDtoData = OfferDto(
                        i.getValue(OfferDto::class.java)!!.exercises,
                        id = i.getValue(OfferDto::class.java)!!.id,
                        image = i.getValue(OfferDto::class.java)!!.image,
                        isBookmarked = i.getValue(OfferDto::class.java)!!.isBookmarked,
                        nexercises = i.getValue(OfferDto::class.java)!!.nexercises,
                        name = i.getValue(OfferDto::class.java)!!.name
                    )
                    dataList.add(offerDtoData.toDomain())
                }
                emit(Resource.Success(dataList))
            } catch (e: Throwable) {
                emit(Resource.Error(e.message ?: ""))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getExercises(id: Int): Flow<Resource<List<Exercise>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val dataList: MutableList<Exercise> = mutableListOf()
                val dataSnapshot: DataSnapshot = withContext(Dispatchers.IO) {
                    firebaseDb.child("offers").child(id.toString()).child("exercises").get().await()
                }
                d("check the datasnap", dataSnapshot.toString())
                for (i in dataSnapshot.children) {
                    val exerciseDtoData = ExerciseDto(
                        desc = i.getValue(ExerciseDto::class.java)!!.desc,
                        id = i.getValue(ExerciseDto::class.java)!!.id,
                        image = i.getValue(ExerciseDto::class.java)!!.image,
                        sets = i.getValue(ExerciseDto::class.java)!!.sets,
                        reps = i.getValue(ExerciseDto::class.java)!!.reps,
                        rest = i.getValue(ExerciseDto::class.java)!!.rest,
                        name = i.getValue(ExerciseDto::class.java)!!.name
                    )
                    dataList.add(exerciseDtoData.toDomain())
                }
                emit(Resource.Success(dataList))
            } catch (e: Throwable) {
                emit(Resource.Error(e.message ?: ""))
            }
            emit(Resource.Loading(false))
        }
    }
}