package com.example.myfitnessapp.domain

class CaloriesBurnedCalculator {
    /**
     * the formula to calculate the calories burned
     *
     * @param mets: Metabolic Equivalent of Task
     * @param weightInKg: the weight in KG
     * @param mins: Minutes of the exercise
     */
    private val caloriesBurnedFormula: (mets: Mets, weightInKg: Int, mins: Int) -> Float =
        { mets, weight, mins -> mets.value * 3.5f * weight / 200 * mins }

    fun calculate(config: Config) =
        caloriesBurnedFormula(config.mets, config.weightInKg, config.mins)

    data class Config(
        val mets: Mets,
        val weightInKg: Int,
        val mins: Int
    )
}

/**
 * Metabolic Equivalent of Task
 *
 * Reference: https://onlinelibrary.wiley.com/doi/pdf/10.1002/clc.4960130809
 */
sealed class Mets(val value: Float)
data object LowStrengthRunning : Mets(8.0f)
data object MediumStrengthRunning : Mets(11.5f)
data object HighStrengthRunning : Mets(16.0f)
data object BrisklyWalking : Mets(4.5f)
data object Swimming : Mets(7.0f)
data object PushUp : Mets(3.8f)
data object PullUp : Mets(3.8f)
data object Squat : Mets(3.8f)
data object Plank : Mets(3.8f)
data object Crunch : Mets(3.8f)
data object Bridge : Mets(3.8f)
