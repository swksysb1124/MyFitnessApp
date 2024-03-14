package com.example.myfitnessapp.model

import androidx.annotation.DrawableRes
import com.example.myfitnessapp.R
import com.example.myfitnessapp.domain.Mets

sealed class Exercise(
    val type: String,
    val name: String,
    @DrawableRes val icon: Int,
    val mets: Mets
) {
    data object LowStrengthRunning : Exercise(
        type = "low_strength_running",
        name = "低強度跑步",
        icon = R.drawable.run,
        mets = Mets(8.0f)
    )

    data object MediumStrengthRunning : Exercise(
        type = "medium_strength_running",
        name = "中強度跑步",
        icon = R.drawable.run,
        mets = Mets(11.5f)
    )

    data object HighStrengthRunning : Exercise(
        type = "high_strength_running",
        name = "高強度跑步",
        icon = R.drawable.run,
        mets = Mets(16.0f)
    )

    data object BrisklyWalking :
        Exercise("briskly_walking", "快走", R.drawable.briskly_walking, Mets(4.5f))

    data object Swimming : Exercise("swimming", "游泳", R.drawable.swimming, Mets(7.0f))
    data object PushUp : Exercise("push_up", "伏地挺身", R.drawable.push_up, Mets(3.8f))
    data object PullUp : Exercise("pull_up", "引體向上", R.drawable.pull_up, Mets(3.8f))
    data object Squat : Exercise("squat", "深蹲", R.drawable.squat, Mets(3.8f))
    data object Plank : Exercise("plank", "棒式", R.drawable.plank, Mets(3.8f))
    data object Crunch : Exercise("crunch", "捲腹", R.drawable.crunch, Mets(3.8f))
    data object Bridge : Exercise("bridge", "橋式", R.drawable.bridge, Mets(3.8f))

    companion object {
        fun getAllExercises() = listOf(
            LowStrengthRunning,
            MediumStrengthRunning,
            HighStrengthRunning,
            BrisklyWalking,
            Swimming,
            PushUp,
            PullUp,
            Squat,
            Plank,
            Crunch,
            Bridge,
        )

        fun find(type: String): Exercise? =
            Exercise::class.sealedSubclasses
                .map { it.objectInstance as Exercise }
                .firstOrNull { it.type == type }
                .let { exercise ->
                    when (exercise) {
                        null -> null
                        else -> exercise
                    }
                }
    }
}
