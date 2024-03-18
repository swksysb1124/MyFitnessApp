package studio.jasonsu.myfitness.repository

import studio.jasonsu.myfitness.database.profile.ProfileDao
import studio.jasonsu.myfitness.database.profile.ProfileEntity
import studio.jasonsu.myfitness.model.Profile

class PersistProfileRepository(
    private val profileDao: ProfileDao
) : ProfileRepository {
    override suspend fun getProfile(): Profile? {
        return profileDao.getProfileById(Profile.PRIMARY_PROFILE_ID)?.toProfile()
    }

    override suspend fun saveProfile(newProfile: Profile) {
        val entity = newProfile.toEntity()
        profileDao.insertProfile(entity)
    }

    override suspend fun updateProfile(updatedProfile: Profile) {
        val entity = updatedProfile.toEntity()
        profileDao.updateProfile(entity)
    }

    private fun ProfileEntity.toProfile() = Profile(id, height, weight)
    private fun Profile.toEntity() = ProfileEntity(Profile.PRIMARY_PROFILE_ID, height, weight)
}