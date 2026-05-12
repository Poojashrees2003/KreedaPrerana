package com.example.kreedaprerana.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kreedaprerana.dao.AthleteDao
import com.example.kreedaprerana.dao.TrailDao
import com.example.kreedaprerana.model.Athlete
import com.example.kreedaprerana.model.Trail

@Database(
    entities = [Athlete::class, Trail::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun athleteDao(): AthleteDao

    abstract fun trailDao(): TrailDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "kreeda_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }
}