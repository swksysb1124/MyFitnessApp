package com.example.myfitnessapp.model

import org.junit.Test

class ExerciseTest {

    @Test
    fun testFindExercise() {
        val exerciseSubClasses = ExerciseType::class.sealedSubclasses
        val allFound = exerciseSubClasses.all {
            ExerciseType.find((it as ExerciseType).type) != null
        }
        println(allFound)
    }
}