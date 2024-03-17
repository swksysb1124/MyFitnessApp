package com.example.myfitnessapp.repository

import com.example.myfitnessapp.database.profile.ProfileDao
import com.example.myfitnessapp.database.profile.ProfileEntity
import com.example.myfitnessapp.model.Profile

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