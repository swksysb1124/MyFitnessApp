package com.example.myfitnessapp.repository

import com.example.myfitnessapp.model.Profile

class MockProfileRepository : ProfileRepository {
    /**
     * cached profile, default height = 169.5, default weight = 82.30
     */
    private var profile = Profile(height = 169, weight = 82)

    override fun getProfile(): Profile {
        return profile
    }

    override fun saveProfile(newProfile: Profile) {
        profile = newProfile
    }
}