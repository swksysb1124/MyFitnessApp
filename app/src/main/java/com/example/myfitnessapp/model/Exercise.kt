package com.example.myfitnessapp.model

data class Exercise(
    override val name: String,
    override val durationInSecond: Int,
    val lessonId: Long? = null,
    val type: ExerciseType
) : Activity {
    companion object {
        const val DefaultDuration = 60
        fun create(
            type: ExerciseType,
            durationInSecond: Int,
            lessonId: Long? = null
        ): Exercise = Exercise(
            name = type.name,
            durationInSecond = durationInSecond,
            lessonId = lessonId,
            type = type
        )
    }
}