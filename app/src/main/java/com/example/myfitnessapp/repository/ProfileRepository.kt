package com.example.myfitnessapp.repository

import com.example.myfitnessapp.model.Profile

class ProfileRepository {
    fun getProfile(): Profile {
        return Profile(
            height = 169.5,
            weight = 82.30
        )
    }
}