package studio.jasonsu.myfitness.model

import org.junit.Test

class ExerciseTest {

    @Test
    fun testFindExercise() {
        val metaDataSubClasses = ExerciseMetaData::class.sealedSubclasses
        val allFound = metaDataSubClasses.all {
            ExerciseMetaData.find((it as ExerciseMetaData).key) != null
        }
        println(allFound)
    }
}