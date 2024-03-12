package com.example.myfitnessapp.model

import androidx.annotation.DrawableRes
import com.example.myfitnessapp.R
import com.example.myfitnessapp.domain.Mets

sealed class Exercise(
    val name: String,
    @DrawableRes val icon: Int,
    val mets: Mets
) {
    data object LowStrengthRunning : Exercise("低強度跑步", R.drawable.run, Mets(8.0f))
    data object MediumStrengthRunning : Exercise("中強度跑步", R.drawable.run, Mets(11.5f))
    data object HighStrengthRunning : Exercise("高強度跑步", R.drawable.run, Mets(16.0f))
    data object BrisklyWalking : Exercise("快走", R.drawable.briskly_walking, Mets(4.5f))
    data object Swimming : Exercise("游泳", R.drawable.swimming, Mets(7.0f))
    data object PushUp : Exercise("伏地挺身", R.drawable.push_up, Mets(3.8f))
    data object PullUp : Exercise("引體向上", R.drawable.pull_up, Mets(3.8f))
    data object Squat : Exercise("深蹲", R.drawable.squat, Mets(3.8f))
    data object Plank : Exercise("棒式", R.drawable.plank, Mets(3.8f))
    data object Crunch : Exercise("捲腹", R.drawable.crunch, Mets(3.8f))
    data object Bridge : Exercise("橋式", R.drawable.bridge, Mets(3.8f))

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
    }
}
