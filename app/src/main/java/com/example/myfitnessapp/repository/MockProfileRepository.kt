package com.example.myfitnessapp.repository

import com.example.myfitnessapp.model.Profile

class MockProfileRepository : ProfileRepository {
    /**
     * cached profile, default height = 169.5, default weight = 82.30
     */
    private var profile = Profile(1, height = 169, weight = 82)

    override suspend fun getProfile(): Profile {
        return profile
    }

    override suspend fun saveProfile(newProfile: Profile) {
        profile = newProfile
    }
}