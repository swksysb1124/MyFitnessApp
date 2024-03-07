package com.example.myfitnessapp.domain

class CaloriesBurnedCalculator {
    /**
     * the formula to calculate the calories burned
     *
     * @param mets: Metabolic Equivalent of Task
     * @param weightInKg: the weight in KG
     * @param mins: Minutes of the exercise
     */
    private val caloriesBurnedFormula: (mets: Mets, weightInKg: Double, mins: Double) -> Double =
        { mets, weight, mins -> mets.value * 3.5f * weight / 200 * mins }

    fun calculate(mets: Mets, weightInKg: Double, mins: Double) =
        caloriesBurnedFormula(mets, weightInKg, mins)
}

/**
 * Metabolic Equivalent of Task
 *
 * Reference: https://onlinelibrary.wiley.com/doi/pdf/10.1002/clc.4960130809
 */
data class Mets(val value: Float)
