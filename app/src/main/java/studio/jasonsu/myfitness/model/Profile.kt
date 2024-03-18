package studio.jasonsu.myfitness.model

data class Profile(
    val id: Int,
    val height: Int,
    val weight: Int
) {
    companion object {
        const val PRIMARY_PROFILE_ID = 1
        fun createPrimaryProfile(height: Int, weight: Int) =
            Profile(PRIMARY_PROFILE_ID, height, weight)
    }
}