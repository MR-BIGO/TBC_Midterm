package com.example.tbc_midterm_project.data.mapper

import com.example.tbc_midterm_project.data.model.OfferDto
import com.example.tbc_midterm_project.domain.model.Offer

fun OfferDto.toDomain(): Offer {
    return Offer(
        id = this.id,
        exercises = this.exercises!!.map { it.toDomain() },
        image = this.image,
        isBookmarked = this.isBookmarked,
        nExercises = this.nexercises,
        name = this.name
    )
}