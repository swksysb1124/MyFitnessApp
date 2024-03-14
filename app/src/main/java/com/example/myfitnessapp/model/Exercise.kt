package com.example.myfitnessapp.model

data class Exercise(
    override val name: String,
    override val durationInSecond: Int,
    val type: ExerciseType
) : Activity {
    companion object {
        const val DefaultDuration = 60
        fun create(type: ExerciseType, durationInSecond: Int): Exercise =
            Exercise(type.name, durationInSecond, type)
    }
}