package com.example.myfitnessapp.model

import org.junit.Test

class ExerciseTest {

    @Test
    fun testFindExercise() {
        val exerciseSubClasses = Exercise::class.sealedSubclasses
        val allFound = exerciseSubClasses.all {
            Exercise.find((it as Exercise).type) != null
        }
        println(allFound)
    }
}