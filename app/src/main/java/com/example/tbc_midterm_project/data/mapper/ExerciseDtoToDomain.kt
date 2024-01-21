package com.example.tbc_midterm_project.data.mapper

import com.example.tbc_midterm_project.data.model.ExerciseDto
import com.example.tbc_midterm_project.domain.model.Exercise

fun ExerciseDto.toDomain(): Exercise {
    return Exercise(
        id = this.id,
        desc = this.desc,
        image = this.image,
        name = this.name,
        reps = this.reps,
        sets = this.sets,
        rest = this.rest
        )
}