package com.example.tbc_midterm_project.data.repository

import com.example.tbc_midterm_project.data.common.Resource
import com.example.tbc_midterm_project.domain.model.Exercise
import com.example.tbc_midterm_project.domain.model.Offer
import com.example.tbc_midterm_project.domain.repository.IFirebaseDatabaseRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FirebaseDatabaseRepositoryImpl @Inject constructor(private val firebaseDb: DatabaseReference) :
    IFirebaseDatabaseRepository {
    override suspend fun getOffers(): Flow<Resource<List<Offer>>> {
        return flow {
            emit(Resource.Loading(true))
            val offerList = mutableListOf<Offer>()
            var emittableError = ""
            try {
                firebaseDb.child("offers").addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            for (i in snapshot.children) {
                                val offerItem = i.getValue(Offer::class.java)
                                offerList.add(offerItem!!)
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        emittableError = error.toString()
                    }

                })
                emit(Resource.Success(offerList))
                emit(Resource.Error(emittableError))
                emit(Resource.Loading(false))
            } catch (e: Throwable) {
                emit(Resource.Error(e.message ?: ""))
            }
        }
    }

    override suspend fun getExercises(id: Int): Flow<Resource<List<Exercise>>> {
        return flow {
            emit(Resource.Loading(true))
            val exercisesList = mutableListOf<Exercise>()
            var emittableError = ""
            try {
                firebaseDb.child("offers").child(id.toString())
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                for (i in snapshot.children) {
                                    val exerciseItem = i.getValue(Exercise::class.java)
                                    exercisesList.add(exerciseItem!!)
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            emittableError = error.toString()
                        }
                    })
                emit(Resource.Success(exercisesList))
                emit(Resource.Error(emittableError))
                emit(Resource.Loading(false))
            } catch (e: Throwable) {
                emit(Resource.Error(e.message ?: ""))
            }
        }
    }
}