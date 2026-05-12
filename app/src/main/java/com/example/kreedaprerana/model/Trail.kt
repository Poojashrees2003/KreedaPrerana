package com.example.kreedaprerana.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trails")
data class Trail(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val athleteName: String,

    val testType: String,

    val result: Double,

    val unit: String,

    val score: Int,

    val date: String
)