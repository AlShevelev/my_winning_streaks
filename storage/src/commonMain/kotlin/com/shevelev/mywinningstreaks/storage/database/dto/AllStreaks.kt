package com.shevelev.mywinningstreaks.storage.database.dto

data class AllStreaks(
    val streaks: List<Streak>,
    val streakIntervals: List<StreakInterval>,
)