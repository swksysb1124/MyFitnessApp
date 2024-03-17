package com.example.myfitnessapp.repository

import com.example.myfitnessapp.model.Profile

interface ProfileRepository {
    suspend fun getProfile(): Profile
    suspend fun saveProfile(newProfile: Profile)
}