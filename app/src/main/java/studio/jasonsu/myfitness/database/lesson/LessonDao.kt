package studio.jasonsu.myfitness.database.lesson

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface LessonDao {
    @Insert
    suspend fun insertLesson(lesson: LessonEntity): Long

    @Query("SELECT * FROM lesson")
    suspend fun getAllLessons(): List<LessonEntity>

    @Query("SELECT * FROM lesson WHERE id = :lessonId")
    suspend fun getLessonById(lessonId: Int): LessonEntity

    @Query("SELECT * FROM lesson WHERE id in (:lessonIds)")
    suspend fun getLessonsByIds(lessonIds: List<Int>): List<LessonEntity>

    @Update
    suspend fun updateLesson(lesson: LessonEntity)

    @Delete
    suspend fun deleteLesson(lesson: LessonEntity)

    @Query("DELETE FROM lesson WHERE id = :lessonId")
    suspend fun deleteLessonById(lessonId: Int)

    @Query("DELETE FROM lesson WHERE id in (:lessonIds)")
    suspend fun deleteLessonsByIds(lessonIds: List<Int>)
}