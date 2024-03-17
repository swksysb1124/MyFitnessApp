package com.example.myfitnessapp.database

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface ProfileDao {
    @Insert
    suspend fun insertProfile(profileEntity: ProfileEntity)
    @Query("SELECT * From profile WHERE id = :profileId")
    suspend fun getProfileById(profileId: Int): ProfileEntity
    @Update
    suspend fun updateProfile(profileEntity: ProfileEntity)
}