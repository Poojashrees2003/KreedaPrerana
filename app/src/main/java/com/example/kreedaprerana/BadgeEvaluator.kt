package com.example.kreedaprerana.util

import com.example.kreedaprerana.model.Badge

object DistrictBenchmark {
    const val MIN_SCORE = 150
    const val MIN_TRAILS = 5
    const val MIN_AVERAGE_SCORE = 30
}

fun getDistrictBadges(score: Int, totalTrails: Int, allScores: List<Int>): List<Badge> {

    return listOf(
        Badge(
            id = "1",
            title = "First Step",
            description = "Complete 1 trail",
            unlocked = totalTrails >= 1
        ),
        Badge(
            id = "2",
            title = "5 Trails",
            description = "Complete 5 trails",
            unlocked = totalTrails >= 5
        ),
        Badge(
            id = "3",
            title = "10 Trails",
            description = "Complete 10 trails",
            unlocked = totalTrails >= 10
        ),
        Badge(
            id = "4",
            title = "Champion",
            description = "Score above 80",
            unlocked = allScores.any { it >= 80 }
        )
    )
}