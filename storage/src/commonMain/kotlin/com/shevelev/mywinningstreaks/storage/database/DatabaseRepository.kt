package com.shevelev.mywinningstreaks.storage.database

import com.shevelev.mywinningstreaks.storage.database.dto.Streak
import com.shevelev.mywinningstreaks.storage.database.dto.StreakInterval
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
interface DatabaseRepository {
    suspend fun getAllStreaks(): List<Streak>

    suspend fun addStreak(streak: Streak)

    suspend fun updateStreakTitle(id: Long, title: String)

    suspend fun deleteStreak(id: Long)

    suspend fun addStreakInterval(interval: StreakInterval, streakId: Long)

    suspend fun updateToValueOfStreakInterval(intervalId: Long, to: Instant)

    suspend fun getStreak(id: Long): Streak
}