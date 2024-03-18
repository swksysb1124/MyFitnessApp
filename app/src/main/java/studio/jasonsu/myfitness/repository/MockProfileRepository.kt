package studio.jasonsu.myfitness.repository

import studio.jasonsu.myfitness.model.Profile
import kotlinx.coroutines.delay

class MockProfileRepository : ProfileRepository {
    /**
     * cached profile, default height = 169.5, default weight = 82.30
     */
    private var profile: Profile? = null

    override suspend fun getProfile(): Profile? {
        delay(500)
        return profile
    }

    override suspend fun saveProfile(newProfile: Profile) {
        profile = newProfile
    }

    override suspend fun updateProfile(newProfile: Profile) {
        profile = newProfile
    }
}