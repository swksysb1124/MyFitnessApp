package studio.jasonsu.myfitness.database.profile

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProfileDao {
    @Insert
    suspend fun insertProfile(profileEntity: ProfileEntity)
    @Query("SELECT * From profile WHERE id = :profileId")
    suspend fun getProfileById(profileId: Int): ProfileEntity?
    @Update
    suspend fun updateProfile(profileEntity: ProfileEntity)
}