package studio.jasonsu.myfitness.model

data class Exercise(
    override val name: String,
    override val durationInSecond: Int,
    val lessonId: Long? = null,
    val metaData: ExerciseMetaData
) : Activity {
    companion object {
        const val DefaultDuration = 60
        fun create(
            metaData: ExerciseMetaData,
            durationInSecond: Int,
            lessonId: Long? = null
        ): Exercise = Exercise(
            name = metaData.name,
            durationInSecond = durationInSecond,
            lessonId = lessonId,
            metaData = metaData
        )
    }
}