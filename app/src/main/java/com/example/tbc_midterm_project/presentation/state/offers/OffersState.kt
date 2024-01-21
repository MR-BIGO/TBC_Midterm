package com.example.tbc_midterm_project.presentation.state.offers

import com.example.tbc_midterm_project.presentation.model.exercise_items.OfferPres

data class OffersState(
    val isLoading: Boolean = false,
    val data: List<OfferPres>? = null,
    val error: String? = null
)
