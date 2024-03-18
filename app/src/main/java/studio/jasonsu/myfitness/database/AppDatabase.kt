package studio.jasonsu.myfitness.database

import androidx.room.Database
import androidx.room.RoomDatabase
import studio.jasonsu.myfitness.database.exercise.ExerciseDao
import studio.jasonsu.myfitness.database.exercise.ExerciseEntity
import studio.jasonsu.myfitness.database.lesson.LessonDao
import studio.jasonsu.myfitness.database.lesson.LessonEntity
import studio.jasonsu.myfitness.database.profile.ProfileDao
import studio.jasonsu.myfitness.database.profile.ProfileEntity

@Database(
    entities = [
        LessonEntity::class,
        ExerciseEntity::class,
        ProfileEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun lessonDao(): LessonDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun profileDao(): ProfileDao
}