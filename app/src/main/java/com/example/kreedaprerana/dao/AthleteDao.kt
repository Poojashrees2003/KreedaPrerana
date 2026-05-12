package com.example.kreedaprerana.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.kreedaprerana.model.Athlete
import kotlinx.coroutines.flow.Flow

@Dao
interface AthleteDao {

    @Insert
    suspend fun insertAthlete(athlete: Athlete)

    @Query("SELECT * FROM athletes")
    fun getAllAthletes(): Flow<List<Athlete>>
}