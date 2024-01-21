package com.example.tbc_midterm_project.presentation.screen.offers

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbc_midterm_project.data.common.Resource
import com.example.tbc_midterm_project.domain.repository.IFirebaseDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OffersFragmentViewModel @Inject constructor(private val repository: IFirebaseDatabaseRepository): ViewModel() {

    init {
        getOffers()
    }
    private fun getOffers(){
        viewModelScope.launch {
            repository.getOffers().collect{
                when(it){
                    is Resource.Success -> d("checking offers", it.data.toString())
                    is Resource.Error -> {d("checking offers", it.error)}
                    is Resource.Loading -> {}
                }
            }
        }

    }
}