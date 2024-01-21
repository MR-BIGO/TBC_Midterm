package com.example.tbc_midterm_project.domain.use_case.calculator

import com.example.tbc_midterm_project.data.common.Resource
import com.example.tbc_midterm_project.presentation.model.calculator.AnswerItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.math.RoundingMode
import javax.inject.Inject

class CalculatorUseCase @Inject constructor() {

    operator fun invoke(
        oldAnswers: List<AnswerItem>,
        sex: String,
        age: Int,
        weight: Double,
        height: Double
    ): Flow<Resource<List<AnswerItem>>> {

        return flow {
            emit(Resource.Loading(true))
            val newAnswers: MutableList<AnswerItem> = mutableListOf()
            val bmiItem = AnswerItem(
                oldAnswers[0].section,
                oldAnswers[0].icon,
                calculateBmi(weight, height),
                calculateBmiMessage(calculateBmi(weight, height))
            )
            val heartRateItem =
                AnswerItem(oldAnswers[1].section, oldAnswers[1].icon, calculateHeartRate(age))
            val weightItem = AnswerItem(
                oldAnswers[2].section,
                oldAnswers[2].icon,
                calculateHealthyWeight(sex, height)
            )
            val waterItem =
                AnswerItem(oldAnswers[3].section, oldAnswers[3].icon, calculateWaterIntake(weight))
            newAnswers.apply {
                add(bmiItem)
                add(heartRateItem)
                add(weightItem)
                add(waterItem)
            }
            emit(Resource.Success(newAnswers))
            emit(Resource.Loading(false))
        }
    }

    private fun calculateBmi(weight: Double, height: Double): Double {
        return (weight / ((height / 100) * (height / 100))).toBigDecimal().setScale(1, RoundingMode.DOWN).toDouble()
    }

    private fun calculateHeartRate(age: Int): Double {
        return (220.0 - age).toBigDecimal().setScale(1, RoundingMode.DOWN).toDouble()
    }

    private fun calculateHealthyWeight(sex: String, height: Double): Double {
        return if (sex == "Male") {
            (50 + (0.91 * (height - 152.4))).toBigDecimal().setScale(1, RoundingMode.DOWN).toDouble()
        } else {
            (45.5 + (0.91 * (height - 152.4))).toBigDecimal().setScale(1, RoundingMode.DOWN).toDouble()
        }
    }

    private fun calculateWaterIntake(weight: Double): Double {
        return (weight / 30).toBigDecimal().setScale(1, RoundingMode.DOWN).toDouble()
    }

    private fun calculateBmiMessage(bmi: Double): String {
        if (bmi < 16) {
            return "< 16 - Severely Underweight";
        } else if (bmi >= 16 && bmi < 18.5) {
            return "16 - 18.5 - Underweight";
        } else if (bmi >= 18.5 && bmi < 25) {
            return "18.5 - 25 - Healthy Weight";
        } else if (bmi >= 25 && bmi < 30) {
            return "25 - 30 - Overweight";
        } else if (bmi >= 30) {
            return "> 30 - Obese";
        }
        return ""
    }
}