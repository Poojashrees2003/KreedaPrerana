package com.example.kreedaprerana.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kreedaprerana.model.Trail
import kotlinx.coroutines.flow.Flow

@Dao
interface TrailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrail(trail: Trail)

    @Query("SELECT * FROM trails ORDER BY id DESC")
    fun getAllTrails(): Flow<List<Trail>>

    // ✅ DELETE TRAIL
    @Delete
    suspend fun deleteTrail(trail: Trail)
}