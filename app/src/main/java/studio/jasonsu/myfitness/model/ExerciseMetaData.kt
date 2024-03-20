package studio.jasonsu.myfitness.model

import androidx.annotation.DrawableRes
import androidx.annotation.Keep
import studio.jasonsu.myfitness.R
import studio.jasonsu.myfitness.domain.Mets

@Keep
sealed class ExerciseMetaData(
    val key: String,
    val name: String,
    @DrawableRes val icon: Int,
    val mets: Mets,
    val suggestedDurationInSecond: Int
) {
    @Keep
    data object LowStrengthRunning : ExerciseMetaData(
        key = "low_strength_running",
        name = "低強度跑步",
        icon = R.drawable.run,
        mets = Mets(8.0f),
        suggestedDurationInSecond = 20 * 60
    )

    @Keep
    data object MediumStrengthRunning : ExerciseMetaData(
        key = "medium_strength_running",
        name = "中強度跑步",
        icon = R.drawable.run,
        mets = Mets(11.5f),
        suggestedDurationInSecond = 45 * 60
    )

    @Keep
    data object HighStrengthRunning : ExerciseMetaData(
        key = "high_strength_running",
        name = "高強度跑步",
        icon = R.drawable.run,
        mets = Mets(16.0f),
        suggestedDurationInSecond = 60 * 60
    )

    @Keep
    data object BrisklyWalking : ExerciseMetaData(
        key = "briskly_walking",
        name = "快走",
        icon = R.drawable.briskly_walking,
        mets = Mets(4.5f),
        suggestedDurationInSecond = 20 * 60
    )

    @Keep
    data object Swimming : ExerciseMetaData(
        key = "swimming",
        name = "游泳",
        icon = R.drawable.swimming,
        mets = Mets(7.0f),
        suggestedDurationInSecond = 60 * 60
    )

    @Keep
    data object PushUp : ExerciseMetaData(
        key = "push_up",
        name = "伏地挺身",
        icon = R.drawable.push_up,
        mets = Mets(3.8f),
        suggestedDurationInSecond = 30
    )

    @Keep
    data object PullUp : ExerciseMetaData(
        key = "pull_up",
        name = "引體向上",
        icon = R.drawable.pull_up,
        mets = Mets(3.8f),
        suggestedDurationInSecond = 30
    )

    @Keep
    data object Squat : ExerciseMetaData(
        key = "squat",
        name = "深蹲",
        icon = R.drawable.squat,
        mets = Mets(3.8f),
        suggestedDurationInSecond = 30
    )

    @Keep
    data object Plank : ExerciseMetaData(
        key = "plank",
        name = "棒式",
        icon = R.drawable.plank,
        mets = Mets(3.8f),
        suggestedDurationInSecond = 30
    )

    @Keep
    data object Crunch : ExerciseMetaData(
        key = "crunch",
        name = "捲腹",
        icon = R.drawable.crunch,
        mets = Mets(3.8f),
        suggestedDurationInSecond = 30
    )

    @Keep
    data object Bridge : ExerciseMetaData(
        key = "bridge",
        name = "橋式",
        icon = R.drawable.bridge,
        mets = Mets(3.8f),
        suggestedDurationInSecond = 30
    )

    companion object {
        val All = listOf(
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

        fun find(type: String): ExerciseMetaData? =
            ExerciseMetaData::class.sealedSubclasses
                .map { it.objectInstance as ExerciseMetaData }
                .firstOrNull { it.key == type }
                .let { exercise ->
                    when (exercise) {
                        null -> null
                        else -> exercise
                    }
                }
    }
}
