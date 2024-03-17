package com.example.myfitnessapp.repository

import com.example.myfitnessapp.model.Profile

interface ProfileRepository {
    fun getProfile(): Profile
    fun saveProfile(newProfile: Profile)
}