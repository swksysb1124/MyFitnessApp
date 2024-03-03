package com.example.myfitnessapp.model

import androidx.annotation.DrawableRes

data class Exercise(
    val name: String,
    val duration: Long,
    @DrawableRes val icon: Int? = null
)
