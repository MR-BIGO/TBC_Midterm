package com.example.tbc_midterm_project.presentation.mapper

import com.example.tbc_midterm_project.domain.model.Exercise
import com.example.tbc_midterm_project.presentation.model.exercise_items.ExercisePres

fun Exercise.toPres(): ExercisePres = ExercisePres(
    id = this.id,
    image = this.image,
    desc = this.desc,
    rest = this.rest,
    name = this.name,
    reps = this.reps,
    sets = this.sets
)