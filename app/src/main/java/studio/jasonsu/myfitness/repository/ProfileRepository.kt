package studio.jasonsu.myfitness.repository

import studio.jasonsu.myfitness.model.Profile

interface ProfileRepository {
    suspend fun getProfile(): Profile?
    suspend fun saveProfile(newProfile: Profile)
    suspend fun updateProfile(updatedProfile: Profile)
}