package com.example.tbc_midterm_project.presentation.mapper

import com.example.tbc_midterm_project.domain.model.Offer
import com.example.tbc_midterm_project.presentation.model.exercise_items.OfferPres

fun Offer.toPres(): OfferPres = OfferPres(
    id = this.id,
    image = this.image,
    exercises = this.exercises,
    nExercises = this.nExercises,
    isBookmarked = this.isBookmarked,
    name = this.name
)